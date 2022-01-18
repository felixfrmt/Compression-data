package compress

/** The Run-length encoding compression method */
class RLE[T] extends Compressor[T, Seq[(T, Int)]]
  {

    /** Fonction récursive de la fonction compress avec un accumulateur k qui compte le nombre de répétitions consécutives du caractère en tête de la séquence
      * @param msg la séquence de caractères (T)
      * @param k le nombre de répétition consécutives de la tête de msg
      * @tparam T est le type de symbole
      * @return Une séquence de tuple avec un premier l'élément de type T et en deuxième le nombre de répétitions consécutives
      */
    def compressRLE(msg : Seq[T], k : Int): Seq[(T, Int)] = 
    {
      /* On regarde la taille du message en entrée */
    	msg.length match {
        /* Condition d'arrêt */
        case 0 => Seq()         /* Si la taille est vide on retourne une séquence vide */
    		case 1 => Seq((msg(0), k))    /* S'il n'y a qu'un seul elément on retourne une séquence avec cet élément et son accumulateur associé */
    		/* Appel récursif */
        case _ => if (msg(0) == msg(1)){
                /* Si le premier caractère et le 2eme sont identiques, on rapelle récursivement la fct avec un le reste du message sans le premier élément et on augmente k de 1 */
    						compressRLE(msg.tail, k + 1)   
    					}
    					else {
    						/* On crée un tuple avec l'élément en tête de la séquence et l'accumulateur k */
                /* S'ils sont différents, on rapelle récursivement la fct avec un le reste du message sans le premier élément et on remet k à 1 */
                Seq((msg.head, k)) ++ compressRLE(msg.tail, 1)
    					}
    	}
    }

    /** Appel de la fonction récursive compressRLE avec ajout d'un acculateur
      * @param msg la séquence de caractères (T)
      * @tparam T est le type de symbole
      * @return Une séquence de tuple avec un premier l'élément de type T et en deuxième le nombre de répétitions consécutives
      */
    /** @inheritdoc */
    def compress(msg : Seq[T]) : Seq[(T, Int)] = 
    {
    	compressRLE(msg, 1)
    }

    /** Crée à partir du tuple (elem) en entrée une séquence de symbole T identique avec n itérations (le 2eme élément du tuple)   
      * @param seq la séquence de tuple avec en premier l'élément de type T et en 2eme sont nb d'itérations 
      * @tparam T est le type de symbole
      * @return Une séquence de symbole T
      */
    def uncompressRec(elem : (T, Int)): Seq[T] = 
    {
      /* Pattern matching du éeme élément du tuple */
    	elem._2 match {
        /* Conditions d'arrêt */
        case 0 => Seq()
    		case 1 => Seq(elem._1)
        /* Appel récursif de la fonction uncompressRec avec n-1 répétitions */ 
    		case x if x > 1 => Seq(elem._1) ++ uncompressRec((elem._1, elem._2 - 1))
    	}
    }

    /** Parcours récursif de la séquence en entrée et appelle uncompressRec avec l'élément de la tête de la séquence  
      * @param seq la séquence de tuple avec en premier l'élément de type T et en 2eme sont nb d'itérations 
      * @tparam T est le type de symbole
      * @return Une séquence de symbole T
      */
    def uncompressRLE(seq : Seq[(T, Int)]): Seq[T] = 
    {
      /* Condition d'arrêt */
      /* Si la séquence est vide on retourne une séquence vide */
    	if (seq.isEmpty){
    		Seq()
    	} 
    	else {
        /* on appelle la fct uncompressRec avec l'élément de la tête de la séquence auquel on concatène l'appel récursif pour les autres éléments de la séquence */
    		uncompressRec(seq.head) ++ uncompressRLE(seq.tail)
    	}
    }

    /** Vérifie que le nombre d'itération de chaque élément de type T est bien positif et appel de la fonction récursive uncompressRLE 
      * @param seq la séquence de tuple avec en premier l'élément de type T et en 2eme sont nb d'itérations 
      * @tparam T est le type de symbole
      * @return Une option de séquence de symbole T ou None
      */
    /** @inheritdoc */
    def uncompress(seq : Seq[(T, Int)]) : Option[Seq[T]] = 
    {	
      /* Si chaque 2eme élément de la sequence de tuple est positif on appelle la fct recursive sinon None */
    	if (seq.foldLeft(true)((acc, x) => acc && (x._2 > 0))){
    		Some(uncompressRLE(seq))
    	}
    	else {
    		None
    	}
    }


  }
