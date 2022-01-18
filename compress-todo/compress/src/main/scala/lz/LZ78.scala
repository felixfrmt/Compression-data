package compress.lz

import compress.Compressor

/** The LZ78 compression method */
object LZ78 extends Compressor[Char, Seq[(Int, Char)]]
{
	/** Fonction qui crée un dictionnaire à la base vide à partir du message initial
      * @param msg la séquence de caractères 
      * @param n le nombre de caractères consécutifs à prendre dans la tête de msg
      * @param dic une séquence indexée de chaine de caractères
      * @return Une séquence indexée de chaine de caratères 
      */
	def createDictionaryFromMsg(msg : Seq[Char], n : Int, dic : IndexedSeq[String]) : IndexedSeq[String] = 
	{
		/* Pattern matching de la taille du message */
		msg.size match {
			/* Condition d'arret */
			/* On retourne le dictionnaire */
			case 0	 			=> dic
			/* Condition d'arret */
			/* Si n le nombre de caractères à prendre dans msg est plus grand que la taille du message alors on ajoute juste les n-1 caractères au dictionnaire */
			case x if n > x		=> dic :+ msg.take(n).toString
			/* Appel récursif */
			/* Si le dictionnaire possède déjà les n caratère on rappelle la fct en en prenant n + 1 */
			case _ 				=> 	if (dic.contains(msg.take(n).toString)) {
										createDictionaryFromMsg(msg, n+1, dic)} 
									/* Sinon on retire les n caratères et on les ajoute au dictionnaire, on repart en reprenant qu'un seul caractère */
									else {
										createDictionaryFromMsg(msg.drop(n), 1, dic :+ msg.take(n).toString)
									} 
			}
	}

	/** Fonction qui convertit le dictionnaire (séquence indexée de chaine de caratères) en séquence de couple (index, caractère) 
      * @param dic une séquence indexée de chaine de caractères
      * @param int l'index qui parcours le dictionnaire
      * @return Une séquence avec l'index des caractère précédents et le nouveau caratère 
      */
	def convertToSeq(dic : IndexedSeq[String], index : Int): Seq[(Int, Char)] = 
	{
		/* Pattern matching de la taille du dictionnaire */
		dic.length match {
			/* Condition d'arret */
			case x	if ((x == 0) || (x <= index))										=> 	Seq()
			/* Condition d'arret */
			case x	if ((x  == index + 1) && (dic.count(z => (z == dic(index))) != 1))  => 	Seq((dic.indexOf(dic(index).take(dic(index).length)) + 1, 0)) 
			/* Appel récursif */
			case _ 																		=> 	if (dic(index).length == 1) 
																								{(0, dic(index).head) +: convertToSeq(dic, index + 1)} 
																							else {(dic.indexOf(dic(index).take(dic(index).length-1)) + 1, dic(index).last) +: convertToSeq(dic, index + 1)}
		}
	}

	/** Fonction qui convertit une séquence d'indexes et de caractères en dictionnaire (séquence indexée de chaine de caractères)  
      * @param seq une séquence de tuple entier : index et caractère 
      * @param dic une séquence indexée de chaine de caractères
      * @return Une séquence indexée de chaine de caractères qui correspond au dictionnaire 
      */
	def createDictionaryFromSeq(seq : Seq[(Int, Char)], dic : IndexedSeq[String]): Option[IndexedSeq[String]] = 
	{
		/* Pattern matching de la séquence seq */
		seq match {
			/* Appel récursif */
			/* Si la séquence n'est pas vide */
			/* Si l'index est 0 c'est la première fois que le caractère apparait dans la séquence donc on l'ajoute au dictionnaire */
			case h :: t  => if (h._1 == 0) {createDictionaryFromSeq(t, dic :+ h._2.toString)} 
							/* Sinon on prend la chaine de l'index indiquée et on la concatène avec le caractère */
							else {if (dic.length >= h._1) {createDictionaryFromSeq(t, dic :+ (dic(h._1-1) ++ h._2.toString))} else {None}} 
			/* Condition d'arret */
			case _ 		 => Some(dic)
		}
	}
	
	/** Fonction qui transforme une séquence indexée de chaine de caractères en séquence de caractères
      * @param dic une séquence indexée de chaine de caractères
      * @return Un option d'une séquence de caractères valide ou None 
      */
	def convertToMsg(dic : Option[IndexedSeq[String]]) : Option[Seq[Char]] = 
	{
		//println("Dico : " + dic)
		/* Pattern matching du dictionnaire */
		dic match {
			case None 				=> None
			/* Option du parcours du dictionnaire en accumulant les caractères de la séquence indexée */
			case Some(dico) 		=> Some(dico.foldLeft(Seq() : Seq[Char])((acc, x) => acc ++ x.toSeq)) 
		}
	}

	/** Fonction qui compresse un message avec la méthode LZ78
      * @param msg une séquence de caractères à compresser
      * @return Une séquence avec l'index des caractère précédents et le nouveau caratère 
      */
    /** @inheritdoc */
    def compress(msg : Seq[Char]) : Seq[(Int, Char)] = 
    {
    	/* Création du dictionnaire et conversion */
    	convertToSeq(createDictionaryFromMsg(msg, 1, IndexedSeq()), 0)
    }

    /** Fonction qui décompresse un message avec la méthode LZ78
      * @param res une séquence avec l'index des caractère précédents et le nouveau caratère
      * @return Un option d'une séquence de caratères ou None 
      */
    /** @inheritdoc */
    def uncompress(res : Seq[(Int, Char)]) : Option[Seq[Char]] = 
    {
    	/* Appel de la fct qui reconstitue le dictionnaire et ensuit convertit celui ci */
    	convertToMsg(createDictionaryFromSeq(res, IndexedSeq()))
    }


	def main(args: Array[String]): Unit = 
    {
    	/*val msg : Seq[Char] = "aabab" 
		val msgbis : Seq[Char] = "aababcd" 
		val msgbisbis : Seq[Char] = "aabac" 
		val seq : Seq[(Int, Char)] = Seq((0, 'a'), (1, 'b'), (2, 0))
		val seqbis : Seq[(Int, Char)] = Seq((0, 'a'), (1, 'b'), (2, 'c'), (0, 'd'))
		val seqbisbis : Seq[(Int, Char)] = Seq((0, 'a'), (1, 'b'), (1, 'c'))

  		println("Le dictionnaire du message : " + msg + " est : " + createDictionaryFromMsg(msg, 1, IndexedSeq()))
  		println("Le dictionnaire du message : " + msgbis + " est : " + createDictionaryFromMsg(msgbis, 1, IndexedSeq()))
  		println("Le dictionnaire du message : " + msgbisbis + " est : " + createDictionaryFromMsg(msgbisbis, 1, IndexedSeq()))
  		println()
  		println("La compression du message : " + msg + " donne : " + compress(msg))
  		println("La compression du message : " + msgbis + " donne : " + compress(msgbis))
  		println("La compression du message : " + msgbisbis + " donne : " + compress(msgbisbis))
  		println()

  		println("Le dictionnaire du message compressé : " + seq + " est : " + createDictionaryFromSeq(seq, IndexedSeq()))
  		println("Le dictionnaire du message compressé : " + seqbis + " est : " + createDictionaryFromSeq(seqbis, IndexedSeq()))
  		println("Le dictionnaire du message compressé : " + seqbisbis + " est : " + createDictionaryFromSeq(seqbisbis, IndexedSeq()))
  		println()
  		
  		println("La décompression du message : " + seq + " donne : " + uncompress(seq))
  		println("La décompression du message : " + seqbis + " donne : " + uncompress(seqbis))
  		println("La décompression du message : " + seqbisbis + " donne : " + uncompress(seqbisbis))
  		println()

  		val test : Seq[Char] =  "belle echelle !"
  		val compressed : Seq[(Int, Char)] = compress(test)

  		println()
  		println("Le dictionnaire du message : " + test + " est : " + createDictionaryFromMsg(test, 1, IndexedSeq()))
  		println("La compression du message : " + test + " donne : " + compressed)
  		println("La décompression du message : " + compressed + " donne : " + uncompress(compressed))*/


    	val msg : Seq[Char] = "test compression" 
  		println("\tLa compression du message : " + msg + " donne : " + compress(msg))

    }

}
