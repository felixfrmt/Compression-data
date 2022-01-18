package compress.statistic

import compress.Compressor

/** A statistic compressor relies on the statistics of symbols in source
  * @param source the input source
  * @tparam S type of symbol used in source
  */
abstract class StatisticCompressor[S](source : Seq[S]) extends Compressor[S, Seq[Bit]]
  {
    /** A map giving the occurrences of each symbol in the source sequence */
    /* Utilise la méthode foldLeft pour parcourir la séquence de symbole et pour chaque symbole inexistant dans occurences ajoute le symbole et son nombre d'apparitions */
    val occurrences : Map[S, Int] = source.foldLeft(Map() : Map[S, Int]){case (acc, x) => if (!acc.contains(x)) acc ++ List(x -> source.count(_ == x)) else acc}
    
    /** SHANNON entropy of source */
    /* Applique le calcul de l'entropy avec la map des occurences et leurs valeurs */
    val entropy : Double = occurrences.values.foldLeft(0.0)((acc, x) => acc - ((x.toDouble / source.length) * math.log(x.toDouble / source.length) / math.log(2)))

    /** The sequence of occurrences sorted by count */
    /* Conversion de la map en sequence puis tri de celle-ci avec la méthode sortWith par ordre croissant */
    val orderedCounts : Seq[(S, Int)] = occurrences.toSeq.sortWith(_._2 <= _._2)
    // occurrences.values.foldLeft(Seq())((acc, x) => acc :+ occurrences.values.foldLeft(0)((acc, y) => if (y > acc) ))

    /** The encoding tree (in most cases, depends from `source`) */
    def tree : Option[EncodingTree[S]]

    /** Compresse une séquence de symboles à partir d'un arbre déjà existant (Objet des méthodes)
      * @param msg la séquence de symboles (S)
      * @tparam S est le type de symbole
      * @return Une séquence de Bits issue du message initial
      */
    /** @inheritdoc */
    def compress(msg : Seq[S]): Seq[Bit] = 
    {
      /* Vérifie que l'arbre existe et est initialisé */
      tree match {
        /* Parcours de la séquence de symbole et encode chaque symbole un par un */
        case Some(x) => msg.foldLeft(Seq() : Seq[Bit])((acc, char) => acc ++ x.encode(char).getOrElse(Seq())) 
        case _       => Seq()
      }
    }

    /** Décompresse une séquence de Bits en séquence de symbole si c'est possible sinon None à partir d'un arbre déjà existant (Objet des méthodes)
      * @param res la séquence de bits
      * @return Un option d'une séquence de symbole issue du message encodé
      */
    /** @inheritdoc */
    def uncompress(res: Seq[Bit]): Option[Seq[S]] = 
    {
      /* Vérifie que l'arbre est initialisé et utilise la fonction récursive décode */
      tree match {
        case Some(x) => x.decode(res) 
        case None    => None
      }
    }
  }
