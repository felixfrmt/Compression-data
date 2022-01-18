package compress.statistic

/** Trait for binary encoding trees (integer-labeled binary trees)
  * @tparam S type of symbols (in leaves only)
  */
sealed abstract class EncodingTree[S](val label : Int)
  {
    /* OPERATIONS ON VALUES */

    /** Checks if tree contains given value
      * @param x value to search
      * @return true if the tree has a leaf with value `x`
      */
    def has(x : S) : Boolean = 
    {
      /* pattern matching de l'objet pour savoir si c'est une feuille ou une node */
      this match
      {
        case EncodingLeaf(_, v   ) => (v == x)
        case EncodingNode(_, l, r) => l.has(x) || r.has(x)
      }
    }

    /** Reduce operation on tree values when applying a function on leaves beforehand
      * @param f the function applied to each leaf value
      * @param op the aggregation operation on a node
      * @tparam U the result type of `f`
      * @return the aggregated value of the tree
      */
    def reduceWith[U](f : S => U)(op : (U, U) => U) : U = this match
      {
        case EncodingLeaf(_, v   ) => f(v)
        case EncodingNode(_, l, r) => op((l reduceWith f)(op), (r reduceWith f)(op))
      }

    /** Reduce operation on tree values
      *
      * `t reduce op` is a shorthand for `(t reduceWith {v => v})(op)`
      * @param op the aggregation operation on a node
      * @return the aggregated value of the tree
      */
    def reduce(op : (S, S) => S) : S = (this reduceWith {v => v})(op)


    /* OPERATIONS ON LABELS */

    /** Reduce operation on tree labels when applying a function on leaves beforehand
      * @param fL the function applied to each leaf label
      * @param opL the aggregation operation on a node : first parameter is the node label
      * @tparam A the result type of `f`
      * @return the result of aggregation operation recursively applied to tree
      */
    def reduceLabelWith[A](fL : Int => A)(opL : (Int, A, A) => A) : A = this match
      {
        case EncodingLeaf(lbl, _   ) => fL(lbl)
        case EncodingNode(lbl, l, r) => opL(lbl, (l reduceLabelWith fL)(opL), (r reduceLabelWith fL)(opL))
      }

    /** Reduce operation on tree labels
      *
      * `t reduceLabel opL` is a shorthand for `(t reduceLabelWith {lbl => lbl})(opL)`
      * @param opL the aggregation operation on a node : first parameter is the node label
      * @return the aggregated label of the tree
      */
    def reduceLabel(opL : (Int, Int, Int) => Int) : Int = (this reduceLabelWith identity)(opL)


    /* ENCONDING/DECODING OPERATIONS */

    /** Encodage récursif d'un caractère appartenant à l'arbre 
      * @param x le symbole à encoder
      * @return la sequence de bit du symbole en fct de l'arbre  
      */
    def encodeRec(x : S): Seq[Bit] = 
    {
      this match {
        /* Condition d'arrêt */
        case EncodingLeaf(lbl, v   ) => Seq()
        /* Appel récursif */
        /* Si le symbole est dans la branche gauche de l'arbre on concatène une Séquence (avec un bit Zero) et l'appel récursif avec la partie de l'arbre à gauche et inversement */
        case EncodingNode(lbl, l, r) => l.has(x) match {
                                          case true   => Seq(Zero) ++ l.encodeRec(x) 
                                          case false  => Seq(One) ++ r.encodeRec(x)
                                        } 
      }
    }

    /** Computes the bit sequence corresponding to a tentative leaf value.
      * @param x value to encode
      * @return the corresponding bit sequence of `x` is a leaf of encoding tree, `None` otherwise
      */
    def encode(x : S) : Option[Seq[Bit]] = 
    {
      /* Pattern matching pour vérifier si le symbole appartient bien à l'arbre (objet) sinon None */
      this.has(x) match {
        case true => Some(encodeRec(x))
        case _    => None
      }
    }



    /** Computes the next value corresponding to the beginning of bit sequence (if possible)
      * @param res the bit sequence to decode
      * @return the decoded value and the bit sequence left to be decoded or `None` if current bit sequence does not lead to a leaf in enconding tree
      */
    def decodeOnce(res : Seq[Bit]) : Option[(S, Seq[Bit])] = 
    {
      /* Si c'est une feuille alors on retourne le symbole et la séquence de bits restante dans un type option */
      this match {
        /* Condition d'arrêt */
        case EncodingLeaf(lbl, v   ) => Some((v, res))
        /* Appel Récursif */
        /* Si c'est un noeud et que la séquence de bits est vide on retourne None sinon on re-appelle la fct avec le sous arbre droit ou gauche */
        case EncodingNode(lbl, l, r) => res match {
                                          case h :: t => if (h == Zero){l.decodeOnce(t)} else {r.decodeOnce(t)} 
                                          case _      => None
                                        }
      }
    }
    /** Décode récursivement symbole par symbole à partir de la séquence de bits et un accumulateur
      * @param res la séquence de bits à décoder
      * @param acc la séquence qui accumule les symboles
      * @return la séquence qui accumule les symboles ou None sinon
      */
    def decodeRec(res : Seq[Bit], acc : Seq[S]) : Option[Seq[S]] = 
    {
      this.decodeOnce(res) match {
        case Some((x, y)) => decodeRec(y, acc :+ x) 
        case _            => if (res.isEmpty){Some(acc)} else {None} 
      }
    }

    /** Computes the sequence of values from the sequence of bits
      * @param res the bit sequence to decode
      * @return the sequence of decoded values or `None` otherwise
      */
    def decode(res : Seq[Bit]) : Option[Seq[S]] = 
    {
      res.isEmpty match {
        case true => None
        case _    => this.decodeRec(res, Seq()) 
      }
    }


    /* MISCELLANEOUS */


    /** Crée une séquence de tuple avec les labels et le nombre de branches parcourues dans l'arbre
      * @param acc le nombre de branches
      * @return Séquence de tuple avec le label et la hauteur de la feuille
      */
    def sequenceLblLeafAndLengthCode(acc : Int) : Seq[(Int, Int)] = 
    {
      this match {
        case EncodingLeaf(lbl, v   ) => Seq((lbl, acc))
        /* Appel récursif */
        /* Crée la séquence du sous arbre gauche et la concatène avec celle du sous arbre droit */
        case EncodingNode(lbl, l, r) => l.sequenceLblLeafAndLengthCode(acc + 1) ++ r.sequenceLblLeafAndLengthCode(acc + 1)
      }
    }

    // def sequenceLeafString(): Seq[S] = 
    // {
    //   this match {
    //     case EncodingLeaf(lbl, v   ) => Seq(v)
    //     case EncodingNode(lbl, l, r) => l.sequenceLeafString() ++ r.sequenceLeafString()
    //   }
    // }

    // def meanLengthRec(seqLblLeafAndLengthCode : Seq[(Int, Int)], nbChar : Int, acc : Int) : Int = 
    // seqLblLeafAndLengthCode match {
    //    case ((x, y) :: tail) => meanLengthRec(tail, nbChar, acc + (x * y / nbChar))
    //    case _                => acc
    //  }

    // lazy val meanLength : Double = meanLengthRec(this.sequenceLblLeafAndLengthCode(0), this.label, 0)

    /** Mean length of code associated to encoding tree */
    /* Fait le calcul de la taille moyenne du code à partir de la séquence générée par la fct récursive ci-dessus (sequenceLblLeafAndLengthCode) */
    lazy val meanLength : Double = this.sequenceLblLeafAndLengthCode(0).foldLeft(0.0)((acc, x) => acc + (x._1 * x._2).toDouble / this.label)

    /** @inheritdoc */
    override def toString : String = this match
     {
       case EncodingLeaf(lbl, v   ) => (v, lbl).toString()
       case EncodingNode(lbl, l, r) => s"EncodingNode([$lbl], $l, $r)"
     }
  }
case class EncodingNode[S](override val label : Int, left : EncodingTree[S], right : EncodingTree[S]) extends EncodingTree[S](label)
case class EncodingLeaf[S](override val label : Int, value : S) extends EncodingTree[S](label)

