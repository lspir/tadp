package pokemon

import org.junit.Test
import org.junit.Assert

class AprenderAtaqueTest {
  @Test
  def `Si el ataque no es de un tipo a fin el pokemon queda KO`={
    Assert.assertEquals(KO,Fixture.pokemonAgua.realizar({_.aprenderAtaque(Fixture.ataqueDragon)}).get.estado)
  }
  @Test
  def `Si el ataque es de un tipo a fin lo aprende`={
    Assert.assertTrue(Fixture.pikachu.realizar({_.aprenderAtaque(Fixture.ataqueElectrico)}).isSuccess)
  }
}