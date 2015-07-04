package pokemon

import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Ignore
import scala.util.Try




class PokemonTest {
  @Test
  def `q`={
    assertEquals(1, 1)
  }
  
  @Test
  def `qw`={
    assertEquals(Pelea, Pelea)
  }
  
  @Test
  def wow = {
    val asd:List[String=>Try[Int]]=List(Gimnasio.prueba(1),Gimnasio.prueba(2))
    
   assertEquals(asd.head("hola").get
   , 1)
   assertEquals(asd.tail.head("hola").get
   , 2)
  }
  
  @Test(expected=classOf[IllegalArgumentException])
  def `asd`={
    val aasd=Try(new Poke(-1))
    assertEquals(classOf[IllegalArgumentException],aasd.get)
    //assertEquals(5,aasd)
   }
  
}