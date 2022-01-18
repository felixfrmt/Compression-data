package compress.lz

import compress.Compressor
import Dictionaries._

/** The LZW compression method
  * @param initialDictionary the starting dictionary
  */
class LZW(val initialDictionary : Dictionary = ASCII) extends Compressor[Char, Seq[Int]]
  {

  	def compressRec(dic : IndexedSeq[String], msg : Seq[Char], n : Int): Seq[Int] = 
  	{
  		// println("msg : " + msg)
  		// println("taille : " + msg.length)
  		// println("n : " + n)
  		msg.length match {
  			case 0 					=> Seq()
  			case x if (x == n)		=> if (dic.contains(msg.take(n).toString)){Seq(dic.indexOf(msg.take(n).toString))} 
  										else {dic.indexOf(msg.take(n-1).toString) +: compressRec(dic :+ msg.take(n).toString, msg.drop(n-1), 1)}
  			case _ 					=> if (dic.contains(msg.take(n).toString)){compressRec(dic, msg, n+1)} 
  										else {dic.indexOf(msg.take(n-1).toString) +: compressRec(dic :+ msg.take(n).toString, msg.drop(n-1), 1)}				
  		}
  	}



  	def uncompressRec(seq : Seq[Int], dic : IndexedSeq[String], toDic : String, res : Seq[Char]): Option[Seq[Char]] = 
  	{
  		seq match {
  			case h :: t => 	if (dic.isDefinedAt(h)){
  								if (dic.contains(toDic ++ dic(h))) {uncompressRec(t, dic, toDic ++ dic(h), res ++ dic(h))}
  								else{uncompressRec(t, dic :+ (toDic ++ dic(h)), dic(h), res ++ dic(h))}}	
					  		else {None} 
  			case _ 		=> 	Some(res)
  		}
  	}


    /** @inheritdoc */
    def compress(msg : Seq[Char]) : Seq[Int] = 
    {
    	compressRec(initialDictionary, msg, 1)
    }

    /** @inheritdoc */
    def uncompress(res : Seq[Int]) : Option[Seq[Char]] = 
    {
    	uncompressRec(res, initialDictionary, "", Seq())
    }
  



}



