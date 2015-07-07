package pokemon

import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Ignore
import scala.util.Try
import scala.util.Success
import scala.util.Success




class PokemonTest {
  @Test
  def `pokemon de especice Chamander sube de nivel al subir experiencia`{
    val charmander=Fixture.charmander
    assertEquals(1, charmander.nivel)
    val charmanderNivel2=charmander.subirExperiencia(350)
    assertEquals(2, charmanderNivel2.nivel)
    val charmanderNivel3=charmander.subirExperiencia(1050)
    assertEquals(3, charmanderNivel3.nivel)
    val charmanderNivel4=charmander.subirExperiencia(2450)
    assertEquals(4, charmanderNivel4.nivel)
  }
  
  @Test
  def `Al alcanzar ciertos nivel charmander Evoluciona cambiando su especie`={
    val charmander=Fixture.charmander
    val charmeleonEvolucionado=charmander.subirExperiencia(charmander.especie.obtenerExperienciaParaNivel(16))
    assertEquals(Fixture.charmeleonEspecie, charmeleonEvolucionado.especie)
//    val charizardEvolucionado=charmeleonEvolucionado.subirExperiencia(charmeleonEvolucionado.especie.obtenerExperienciaParaNivel(36))
//    assertEquals(36,charizardEvolucionado.nivel) // FIXME fuera de rango INT
    //assertEquals(Fixture.charmeleonEspecie.evolucion.get.especie, charizardEvolucionado.especie)
   
  }
  
}