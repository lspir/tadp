package pokemon

import org.junit.Test
import org.junit.Assert

class NadarTest {
  @Test
  def `Pokemon de Fuego queda en estado KO al nadar`={
    Assert.assertEquals(KO,Fixture.pokemonFuego.realizar({_.nadar(10)}).get.estado)
  }
  
  @Test
  def `Pokemon de tipo Agua aumenta su velocidad,su energia y su experiencia`={
    val pokemonAgua=Fixture.pokemonAgua.realizar({_.nadar(10)}).get
    Assert.assertEquals(Fixture.pokemonAgua.experiencia+2000, pokemonAgua.experiencia)
    Assert.assertEquals(10/60+Fixture.pokemonAgua.velocidad, pokemonAgua.velocidadOriginal)
    Assert.assertEquals(10+Fixture.pokemonAgua.energia, pokemonAgua.energiaOriginal)
  }
  
}