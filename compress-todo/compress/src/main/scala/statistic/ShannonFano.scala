package compress.statistic

/** The SHANNON-FANO compression method */
class ShannonFano[S](source : Seq[S]) extends StatisticCompressor[S](source)
{
	/** Fait la somme de tous les labels de la séquence de feuilles et noeuds
      * @param seq la séquence de feuilles et noeuds
      * @tparam S est le type de symbole
      * @return La somme des labels de toutes les feuilles et les noeuds  
      */
	def sumAllLabel(seq : Seq[EncodingTree[S]]): Int = 
	{
		/* Parcours de la séquence avec foldLeft et somme des labels */
		seq.foldLeft(0)((acc, x) => acc + x.label)
	}

	/** Découpe récursivement la séquence seq2 en faisant passer les éléments un à un vers la séquence seq1 jusqu'à ce que la somme des labels soit bien répartie dans les 2 séquences
      * @param seq1 la séquence de feuilles et noeuds vide
      * @param seq2 la séquence de tous les symboles insérés dans des feuilles et des noeuds
      * @tparam S est le type de symbole
      * @return Un couple de 2 séquences découpées au au niveau de la moyenne des labels  
      */
	def trunc(seq1 : Seq[EncodingTree[S]], seq2 : Seq[EncodingTree[S]]): (Seq[EncodingTree[S]], Seq[EncodingTree[S]]) = 
	{
		/* Vérifie que la séquence seq2 possède plus d'un élément */
		if (seq2.size > 1) {
			/* Si la somme des éléments de seq1 est plus petit que la moyenne de tous les labels, on ajoute l'élément en tête de seq2 à seq1*/
			if (sumAllLabel(seq1) < sumAllLabel(seq1 ++ seq2) / 2) {
				trunc(seq1 :+ seq2.head, seq2.drop(1)) 
			}
			else {
				/* Sinon on retourne le couple des 2 sequences */
				(seq1, seq2)
			}
		}
		else {
			/* Si la séquence 2 n'a qu'un élément ou moins on retourne une séquence vide et seq2 */
			(seq1, seq2)
		}
	}

	/** Applique l'algorithme de Shonnon-Fano sur une séquence  
      * @param seq la séquence de tous les symboles insérés dans des feuilles et des noeuds
      * @tparam S est le type de symbole
      * @return Un arbre sous la forme d'un noeud ou d'une feuille  
      */
	def shannonFanoRec(seq : Seq[EncodingTree[S]]): EncodingTree[S] = 
	{
		/* Pattern matching de la taille de la séquence */
		seq.size match {
			/* Condition d'arret */
			/* S'il n'y a qu'un symbole ou que le noeud est seul */
			case 1 => 	//println("1 - EncodingNode(seq.head.label + seq.last.label, seq.head, seq.last) " + seq.head)
						seq.head
			/* Condition d'arret */
			/* S'il y a 2 éléments, auxquel cas on crée un noeuds avec les 2 sous ensembles */
			case 2 =>	//println("2 - EncodingNode(seq.head.label + seq.last.label, seq.head, seq.last) " + seq)
						EncodingNode(seq.head.label + seq.last.label, seq.head, seq.last)
			/* Appel Récursif */
			case _ => EncodingNode(shannonFanoRec(trunc(Seq(), seq)._1).label + shannonFanoRec(trunc(Seq(), seq)._2).label, shannonFanoRec(trunc(Seq(), seq)._1), shannonFanoRec(trunc(Seq(), seq)._2))
		}
	}

	/** Converti l'ensemble des occurences d'un message en feuilles avec leurs symboles et leurs nombres d'apparitions
      * @param seq séquence de 2-uplet avec le symbole et le nombre d'apparitions
      * @tparam S est le type de symbole
      * @return Une séquence de feuilles de type EncodingLeaf[S]
      */
	def convert(seq : Seq[(S, Int)]) : Seq[EncodingTree[S]] = 
  	{
  		/* Crée directement une feuille pour chaque symbole de la séquence */
 		seq.foldLeft(Seq() : Seq[EncodingTree[S]])((acc, x) => acc :+ EncodingLeaf[S](x._2, x._1))
  	}

    /** Vérifie que le nombre d'occurences n'est pas vide pour créer l'arbre (en utilisant la fct recursive huffmanRec) et None sinon */
    /** @inheritdoc */
    lazy val tree : Option[EncodingTree[S]] = if (orderedCounts.nonEmpty) {Some(shannonFanoRec(convert(orderedCounts)))} else {None}
}
