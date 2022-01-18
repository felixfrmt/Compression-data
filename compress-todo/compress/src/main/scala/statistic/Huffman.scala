package compress.statistic

/** The HUFFMAN compression method */
class Huffman[S](source : Seq[S]) extends StatisticCompressor[S](source)
  {
    /** Crée une nouvelle feuille
      * @param n le nombre d'apparition du symbole
      * @param s le symbole 
      * @tparam S est le type de symbole
      * @return Une feuille de classe mère EncodingTree[S]
      */
  	def createLeaf(n : Int, s : S): EncodingLeaf[S] = 
  	{
  		new EncodingLeaf[S](n, s)
  	}

    /** Crée un nouveau noeud
      * @param n le nombre d'apparition du symbole dans les sous branches
      * @param l le sous arbre gauche de type EncodingTree[S]
      * @param r le sous arbre droit de type EncodingTree[S]
      * @tparam S est le type de symbole
      * @return Un noeud de classe mère EncodingTree[S]
      */
  	def createNode(n : Int, l : EncodingTree[S], r : EncodingTree[S]): EncodingNode[S] = 
  	{
  		new EncodingNode[S](n, l, r)
  	}

    /** Crée un noeud avec les deux premiers élément d'une séquence de feuille et de noeud et retri la séquence
      * @param seq séquence de feuille et de noeuds qui ont la même classe mère EncodingTree[S]
      * @tparam S est le type de symbole
      * @return Une séquence d'éléments de type EncodingTree[S]
      */
  	def sumNodeLeaf(seq : Seq[EncodingTree[S]]): Seq[EncodingTree[S]] = 
  	{
  		// On fusionne les feuilles (ou node) pour creer une node et on concatenne avec le reste de la sequence sans les 2 premieres
  		// puis on retourne la nouvelle sequence triée
  		(createNode(seq(0).label + seq(1).label, seq(0), seq(1)) +: seq.drop(2)).sortWith(_.label < _.label)
  	}

    /** Crée un arbre à partir d'une séquence de feuille en fusionnant les deux éléments ayants les plus petits labels
      * @param seq séquence de feuille de classe mère EncodingTree[S]
      * @tparam S est le type de symbole
      * @return Une feuille ou un noeud EncodingTree[S] qui forme l'arbre
      */
  	def huffmanRec(seq : Seq[EncodingTree[S]]): EncodingTree[S] = 
  	{
      /* Pattern matching de la taille de la séquence */
  		seq.size match {
        /* Condition d'arret */
        /* Si la séquence n'a qu'un élément l'arbre complet est retourné */
  			case 1 => seq.head
        /* Appel récursif */
        /* Fait la somme des 2 plus petits labels parmis les noeuds et les feuilles */  
  			case _ => huffmanRec(sumNodeLeaf(seq))
  		}
  	}

    /** Converti l'ensemble des occurences d'un message en feuilles avec leurs symboles et leurs nombres d'apparitions
      * @param seq séquence de 2-uplet avec le symbole et le nombre d'apparitions
      * @tparam S est le type de symbole
      * @return Une séquence de feuilles de type EncodingLeaf[S]
      */
  	def convert(seq : Seq[(S, Int)]) : Seq[EncodingTree[S]] = 
  	{
      /* Parcours la séquence et crée des feuilles */
      seq.foldLeft(Seq() : Seq[EncodingTree[S]])((acc, x) => acc :+ createLeaf(x._2, x._1))
  	}

    /** Vérifie que le nombre d'occurences n'est pas vide pour créer l'arbre (en utilisant la fct recursive huffmanRec) et None sinon */
    /** @inheritdoc */
    lazy val tree : Option[EncodingTree[S]] = if (orderedCounts.nonEmpty) {Some(huffmanRec(convert(orderedCounts)))} else {None}
}


