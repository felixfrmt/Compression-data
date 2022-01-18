package compress

/** The Run-length encoding compression method */
object TestRLE{

	// def main(args: Array[String]): Unit = 
 //  	{
 //  		val compressor : RLE[Char] = new RLE[Char]
 //  		val msg = "aaaabbbcbb"
 //  		val code : Seq[(Char, Int)]  = compressor.compress(msg) 
 // 		println("Compression du message :\n" + msg + "\n => " + code)

 // 		val msgUncompressed = compressor.uncompress(code)
 // 		println("Décompression du message :\n" + msg + "\n => " + msgUncompressed)



 //  	}
}

object TestEncodingTree{

	// def main(args: Array[String]): Unit = 
 //  	{
 //  		val lettreA : Char = 'a'
 //  		val lettreB : Char = 'b'
 //  		val lettreC : Char = 'c'
 //  		val lettreD : Char = 'd'
 //  		val msgCourt : Seq[Char] = "abbca"

 //  		val leafA = new compress.statistic.EncodingLeaf[Char](2, lettreA)
 //  		val leafB = new compress.statistic.EncodingLeaf[Char](2, lettreB)
 //  		val leafC = new compress.statistic.EncodingLeaf[Char](1, lettreC)

 //  		val nodeAB = new compress.statistic.EncodingNode[Char](4, leafA, leafB)
 //  		val nodeABC = new compress.statistic.EncodingNode[Char](5, nodeAB, leafC)
  		

 //  		val compressorA = nodeABC.encode(lettreA)
 //  		val compressorB = nodeABC.encode(lettreB)
 //  		val compressorC = nodeABC.encode(lettreC)
 //  		val compressorNothing = Seq(compress.statistic.Zero, compress.statistic.Zero, compress.statistic.Zero)
 //  		val compressorEmpty = Seq()

 //  		// Test de la compression qui marche
 //  		println("La lettre A compressée donne : " + compressorA)
 //  		println("La lettre C compressée donne : " + compressorC)
 //  		// Test de la compression et décompression qui marche
 //  		println("La lettre B compressée et décompressée donne : " + nodeABC.decode(compressorB.getOrElse(Seq())))
 //  		// Test de la compression d'un caractère inexistant dans l'arbre
 //  		println("La lettre qui n'appartient pas à l'arbre D compressée donne : " + nodeABC.encode(lettreD))
 //  		// Test de la compression et décompression qui marche pas car le code n'est pas de la bonne taille
 //  		println("Décompression impossible donne : " + nodeABC.decode(compressorNothing))
 //  		// Test de décompression d'une sequence vide
 //  		println("Décompression sequence vide donne : " + nodeABC.decode(compressorEmpty))


 //  		// Test de has
 //  		println("Est ce que l'arbre possede la lettre B : " + nodeABC.has(lettreB))
 //  		println("Est ce que l'arbre possede la lettre D : " + nodeABC.has(lettreD))

 //  		// Test du calcul de la longueur moyenne 
 //  		println("Longueur moyenne du code \"abbca\" : " + nodeABC.meanLength) 

 //  	}
}

object TestStatisticCompressor { 

	// def main(args: Array[String]): Unit = 
 //  	{
	// 	val msg : Seq[Char] = "acbab"
 //  		val stats = new compress.statistic.Huffman[Char](msg)

 //  		// Test du calcul des occurrences
 //  		println("Occurences des lettres du message : " + stats.occurrences)
 //  		// Test du calcul de l'entropy
 //  		println("Entropy du message : " + stats.entropy)
 //  		// Test du calcul des occurrences triées
 //  		println("Occurences triées du message : " + stats.orderedCounts)
 //  	}
}


object TestHuffman { 

	// def main(args: Array[String]): Unit = 
 //  	{
	// 	val	lettreA : Char = 'a'
 //  		val lettreB : Char = 'b'
 //  		val lettreC : Char = 'c'
 //  		val lettreD : Char = 'd'
 //  		val lettreE : Char = 'e'
 //  		val lettreF : Char = 'f'
 //  		val msgCourt : Seq[Char] = "abbca"

 //  		val msg : Seq[Char] = "abbca"
 //  		val huffmanCompressor = new compress.statistic.Huffman[Char](msg)
 //  		val stats = new compress.statistic.Huffman[Char](msg)

 //  		// Test de la compression de Huffman  
	// 	println("Méthode de Huffman : " + huffmanCompressor.tree.toString)	
	// 	println("Compression du message : " + msg + " selon la méthode de Huffman : " + huffmanCompressor.compress(msg))

	// 	println("Encodage de chaque lettre : ")	
	// 	println("A : " + huffmanCompressor.compress(Seq(lettreA)))	
	// 	println("B : " + huffmanCompressor.compress(Seq(lettreB)))	
	// 	println("C : " + huffmanCompressor.compress(Seq(lettreC)))	
  		
 //  		// Test de la décompression selon la méthode de Huffman 
 //  		val compressed = huffmanCompressor.compress(msg)
	// 	println("Décompression du message : "+ msg + " selon la méthode de Huffman : " + huffmanCompressor.uncompress(compressed))	
 //  		println("Longueur moyenne du code \"abbca\" avec Huffman : " + huffmanCompressor.tree.getOrElse(compress.statistic.EncodingLeaf[Char](2, 'a')).meanLength) 

 //  		// character   Frequency
 //    // a            5
 //    // b           9
 //    // c           12
 //    // d           13
 //    // e           16
 //    // f           45

 //    	val msgLong : Seq[Char] = "aafffabfbbbbbfffffffffffffffbbbccccccdddddcccccddddaaddedeeeeeeeeeedeeeeeffffffffffffcffffffffffffff"
 //  		val huffmanCompressor2 = new compress.statistic.Huffman[Char](msgLong)
		
	// 	println("Méthode de Huffman : " + huffmanCompressor2.tree.toString)	
  		
 //  		println("Encodage de chaque lettre : ")	
	// 	println("A : " + huffmanCompressor2.compress(Seq(lettreA)))	
	// 	println("B : " + huffmanCompressor2.compress(Seq(lettreB)))	
	// 	println("C : " + huffmanCompressor2.compress(Seq(lettreC)))	
	// 	println("D : " + huffmanCompressor2.compress(Seq(lettreD)))	
	// 	println("E : " + huffmanCompressor2.compress(Seq(lettreE)))	
	// 	println("F : " + huffmanCompressor2.compress(Seq(lettreF)))	
 //  	}
}

object TestShannonFano { 

	// def main(args: Array[String]): Unit = 
 //  	{
 //  		val	lettreA : Char = 'a'
 //  		val lettreB : Char = 'b'
 //  		val lettreC : Char = 'c'
 //  		val lettreD : Char = 'd'
 //  		val lettreE : Char = 'e'
 //  		val lettreF : Char = 'f'
 //  		val msgCourt : Seq[Char] = "abbca"

  // 		val msg : Seq[Char] = "abbca"
  // 		val shannonfanoCompressor = new compress.statistic.ShannonFano[Char](msg)
  // 		val stats = new compress.statistic.ShannonFano[Char](msg)
  			
  // 		// Test de la compression de Huffman  
		// println("Méthode de ShannonFano : " + shannonfanoCompressor.tree.toString)	
		// println("Compression du message : " + msg + " selon la méthode de ShannonFano : " + shannonfanoCompressor.compress(msg))

		// println("Encodage de chaque lettre : ")	
		// println("A : " + shannonfanoCompressor.compress(Seq(lettreA)))	
		// println("B : " + shannonfanoCompressor.compress(Seq(lettreB)))	
		// println("C : " + shannonfanoCompressor.compress(Seq(lettreC)))	
  
		// // Test de la décompression selon la méthode de Huffman 
  // 		val compressed = shannonfanoCompressor.compress(msg)
		// println("Décompression du message : "+ msg + " selon la méthode de Huffman : " + shannonfanoCompressor.uncompress(compressed))	
  // 		println("Longueur moyenne du code \"abbca\" avec Huffman : " + shannonfanoCompressor.tree.getOrElse(compress.statistic.EncodingLeaf[Char](2, 'a')).meanLength) 
  // 		println("Entropie du code \"abbca\" avec Huffman : " + shannonfanoCompressor.entropy) 


  // 		// Character   Frequency
  //   	// 	a            22				e : 5  ->  c : 15  ->  a : 22  ->  b : 28  ->  d : 30		SUM : 100
  //   	// 	b            28				e : 5  ->  c : 15  ->  a : 22  ->  b : 28  ||  d : 30		SUM : 70 - 30
  //   	// 	c            15				e : 5  ->  c : 15  ->  a : 22  ||  b : 28  ||  d : 30 		SUM : 42 - 28 - 30
  //   	// 	d            30				e : 5  ->  c : 15  ||  a : 22  ||  b : 28  ||  d : 30		SUM : 20 - 22 - 28 - 30
  //   	// 	e            5				e : 5  ||  c : 15  ||  a : 22  ||  b : 28  ||  d : 30		SUM : 5 - 15 - 22 - 28 - 30

		// val msgLong : Seq[Char] = "aaeaeaaaaaaaaaabcaabbbbaabbbbbbbacdbbdddddddbbbbbbbccbbbbbbbcccceccccaaaacccddddddddddddddddddddddee"
  // 		val shannonfanoCompressor2 = new compress.statistic.ShannonFano[Char](msgLong)
		
  // 		println("Occurences : " + shannonfanoCompressor2.orderedCounts)
  // 		println("SumAllLabel : " + shannonfanoCompressor2.sumAllLabel(shannonfanoCompressor2.convert(shannonfanoCompressor2.orderedCounts)))

		// println("Méthode de Huffman : " + shannonfanoCompressor2.tree.toString)	
  		
  // 		println("Encodage de chaque lettre : ")	
		// println("A : " + shannonfanoCompressor2.compress(Seq(lettreA)))	
		// println("B : " + shannonfanoCompressor2.compress(Seq(lettreB)))	
		// println("C : " + shannonfanoCompressor2.compress(Seq(lettreC)))	
		// println("D : " + shannonfanoCompressor2.compress(Seq(lettreD)))	
		// println("E : " + shannonfanoCompressor2.compress(Seq(lettreE)))	
  	
  		

  		// Character   Frequency
    // 		a            15				a : 15  ->  b : 7  ->  c : 6  ->  d : 6  ->  e : 5		SUM : 39
    // 		b            7				a : 15  ->  b : 7  ||  c : 6  ->  d : 6  ->  e : 5		SUM : 39
    // 		c            6				a : 15  ||  b : 7  ||  c : 6  ||  d : 6  ->  e : 5		SUM : 39
    // 		d            6				a : 15  ||  b : 7  ||  c : 6  ||  d : 6  ||  e : 5		SUM : 39
    // 		e            5				a : 15  ||  b : 7  ||  c : 6  ||  d : 6  ||  e : 5		SUM : 39

		// val msgLong : Seq[Char] = "aeacaaabcaabaaaadabaebbaabccdccddcddebee"
  // 		val shannonfanoCompressor2 = new compress.statistic.ShannonFano[Char](msgLong)
		
  // 		println("Occurences : " + shannonfanoCompressor2.orderedCounts)
  // 		println("SumAllLabel : " + shannonfanoCompressor2.sumAllLabel(shannonfanoCompressor2.convert(shannonfanoCompressor2.orderedCounts)))

		// println("Méthode de Huffman : " + shannonfanoCompressor2.tree.toString)	
  		
  // 		println("Encodage de chaque lettre : ")	
		// println("A : " + shannonfanoCompressor2.compress(Seq(lettreA)))	
		// println("B : " + shannonfanoCompressor2.compress(Seq(lettreB)))	
		// println("C : " + shannonfanoCompressor2.compress(Seq(lettreC)))	
		// println("D : " + shannonfanoCompressor2.compress(Seq(lettreD)))	
		// println("E : " + shannonfanoCompressor2.compress(Seq(lettreE)))	

  	// }
}



object TestLZ78 { 

	// def main(args: Array[String]): Unit = 
	// {
		// val msg : Seq[Char] = "aabab" 
		// val msgbis : Seq[Char] = "aababcd" 

  // 		val LZ78 = new compress.LZ78[Char](msg)
  // 		val LZ78bis = new compress.Compressor[Char, LZ78](msgbis)

	// }	

}



object TestLZW {

	/*def main(args: Array[String]): Unit = 
	{
		
  		val lzw = new compress.lz.LZW

		val msg : Seq[Char] = "aabab" 
		val msgbis : Seq[Char] = "aababcd" 
		val msgbisbis : Seq[Char] = "aabac" 
		val seq : Seq[Int] = lzw.compress(msg)
		val seqbis : Seq[Int] = lzw.compress(msgbis)
		val seqbisbis : Seq[Int] = lzw.compress(msgbisbis)
		val error1 : Seq[Int] = Seq(259)
		val error2 : Seq[Int] = Seq()
	


		println("Le message : " + msg + " compressé suivant la méthode LZW donne : " + seq)
		println("Le message : " + msgbis + " compressé suivant la méthode LZW donne : " + seqbis)
		println("Le message : " + msgbisbis + " compressé suivant la méthode LZW donne : " + seqbisbis)
		println()
		println("Le message : " + seq + " décompressé suivant la méthode LZW donne : " + lzw.uncompress(seq))
		println("Le message : " + seqbis + " décompressé suivant la méthode LZW donne : " + lzw.uncompress(seqbis))
		println("Le message : " + seqbisbis + " décompressé suivant la méthode LZW donne : " + lzw.uncompress(seqbisbis))
		println("Le message : " + error1 + " décompressé suivant la méthode LZW donne : " + lzw.uncompress(error1))
		println("Le message : " + error2 + " décompressé suivant la méthode LZW donne : " + lzw.uncompress(error2))


		val test : Seq[Char] =  "belle echelle"
		val compressed : Seq[Int] =  lzw.compress(test)

  		println()
  		println("La compression du message : " + test + " donne : " + compressed)
  		println("La décompression du message : " + compressed + " donne : " + lzw.uncompress(compressed))



	}*/

}



object TestCompression {

    /*def main(args: Array[String]): Unit = 
    {
        val msg : Seq[Char] = "test compression"
        
        val compressor : RLE[Char] = new RLE[Char]
        println()
        println()
        println("\tCompression du message avec RLE :\n" + msg + "\n => " + compressor.compress(msg))
        println()
        println()

        val huffmanCompressor = new compress.statistic.Huffman[Char](msg)
        println("\tMéthode de Huffman : " + huffmanCompressor.tree.toString)  
        println("\tCompression du message : " + msg + " selon la méthode de Huffman : " + huffmanCompressor.compress(msg))
        println()
        println()

        val shannonFanoCompressor = new compress.statistic.ShannonFano[Char](msg)
        println("\tMéthode de Shannon - Fano : " + shannonFanoCompressor.tree.toString)  
        println("\tCompression du message : " + msg + " selon la méthode de Shannon - Fano : " + shannonFanoCompressor.compress(msg))
        println()
        println()

        val lzw = new compress.lz.LZW
        println("\tLa compression du message : " + msg + " donne : " + lzw.compress(msg))


    }
*/}

object Partiel {
    def main(args: Array[String]): Unit = 
    {
        val test = Some(4)
        println("Some(5).get : " + test.get)

    }
}
