package pokemon

import org.junit.Test
import scala.util.Try
import org.junit.Assert

class GimnasioTest {
  @Test
  def `Ejecutar Rutina con realizar un Ataque`={
   val pokemonFuego=Fixture.pokemonFuego.aprende(Fixture.ataqueDragon)
   val rutina=Fixture.rutinaRealizarAtaque
   val pokemonExperimentadoTry=Gimnasio.ejecutarRutina(rutina, pokemonFuego)
   Assert.assertEquals(pokemonFuego.experiencia+80, pokemonExperimentadoTry.get.experiencia)
  
  }
  
    @Test
  def `Ejecutar Rutina con realizar un Ataque y nadar`={
   val pokemonFuego=Fixture.pokemonFuego.aprende(Fixture.ataqueDragon)
   val kilos=10
   val rutina=Fixture.rutinaAtaquePesas10Kilos
   val pokemonExperimentadoTry=Gimnasio.ejecutarRutina(rutina, pokemonFuego)
   Assert.assertEquals(pokemonFuego.experiencia+80+kilos, pokemonExperimentadoTry.get.experiencia)
  
  }
    
    @Test(expected=classOf[NoSePudoLevantarPesasException])
    def `Rutina con levantarPesas para pokemon Fantasma devuelve un Fail`={
        val pokemonFantasma=Fixture.pokemonFantasma.aprende(Fixture.ataqueDragon)
        val pokemonProblematico=Gimnasio.ejecutarRutina(Fixture.rutinaAtaquePesas10Kilos, pokemonFantasma)
        Assert.assertEquals(classOf[NoSePudoLevantarPesasException],pokemonProblematico.get)
  
    }
    
     @Test
    def `Analizador de Rutina con levantarPesas para pokemon Fantasma devuelve un None`={
        val pokemonFantasma=Fixture.pokemonFantasma.aprende(Fixture.ataqueDragon)
        Assert.assertTrue(Gimnasio.mejorRutinaSegunCriterio(List(Fixture.rutinaAtaquePesas10Kilos), pokemonFantasma, Fixture.criterioMayorExperiencia).isEmpty)
    }
    
    
    @Test
    def `Analizador de Rutinas segun mayor experiencia`={
       val pokemonFuego=Fixture.pokemonFuego.aprende(Fixture.ataqueDragon)
       val rutina1=Fixture.rutinaRealizarAtaque
       val rutina2=Fixture.rutinaAtaquePesas10Kilos      
       Assert.assertEquals("RutinaAtaquePesas",Gimnasio.mejorRutinaSegunCriterio(List(rutina1,rutina2), pokemonFuego, Fixture.criterioMayorExperiencia).get)
    }
    
    
  
}