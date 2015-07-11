package pokemon

import org.junit.Test
import org.junit.Assert

class LevantarPesasTest {
  @Test(expected=classOf[NoSePudoLevantarPesasException])
  def `Pokemon tipo fantasma no puede levantar pesas`={
  val pokemonFantasma=Fixture.pokemonFantasma
  Assert.assertEquals(classOf[NoSePudoLevantarPesasException],pokemonFantasma.realizar({_.levantarPesas(10)}).get)
  
  }
  
    @Test
  def `Pokemon paralizado queda KO al levantar pesas`={
  val pokemon=Fixture.pikachu.copy(estado=Paralizado)
  Assert.assertEquals(KO,pokemon.realizar({_.levantarPesas(10)}).get.estado)
  }
    
      @Test
  def `Si los kilos superan a la fuerza por diez, el pokemon queda paralizado`={
  val pokemon=Fixture.pikachu
  Assert.assertEquals(Paralizado,pokemon.realizar({_.levantarPesas(pokemon.fuerza*10+1)}).get.estado)
  }
         @Test
  def `Si el pokemon es de tipo Pelea gana el doble de experiencia que los kilos`={
  val pokemon=Fixture.pokemonPelea
  val kilos=10
  Assert.assertEquals(pokemon.experiencia+2*kilos,pokemon.realizar({_.levantarPesas(kilos)}).get.experiencia)
  }
               @Test
  def `Si el pokemon es cualquier otro tipo distinto a Pelea y Fanstasma gana de experiencia la cantidad de kilos`={
  val pokemon=Fixture.pikachu
  val kilos=10
  Assert.assertEquals(pokemon.experiencia+kilos,pokemon.realizar({_.levantarPesas(kilos)}).get.experiencia)
  }
}