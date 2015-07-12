package pokemon

import org.junit.Test
import org.junit.Assert

class FingirIntercambioTest {
  @Test
  def `Al ser intercambiado un pokemon con esa condicion de evolucion, evoluciona`={
    Assert.assertEquals(Fixture.machoke.especie.evolucion.get.especie, Fixture.machoke.realizar({_.fingirIntercambio}).get.especie)
  }
  
   @Test
  def `Al ser intercambiado un pokemon sin esa condicion de evolucion, se pone triste aumentando su peso si es macho`={
    Assert.assertEquals(Fixture.pikachu.peso+1, Fixture.pikachu.realizar({_.fingirIntercambio}).get.peso)
    
  }
  
    @Test
  def `Al ser intercambiado un pokemon sin esa condicion de evolucion, se pone triste reduciendo su peso si es hembra`={
    Assert.assertEquals(Fixture.zapdosHembra.peso-10, Fixture.zapdosHembra.realizar({_.fingirIntercambio}).get.peso)
    
  }

} 