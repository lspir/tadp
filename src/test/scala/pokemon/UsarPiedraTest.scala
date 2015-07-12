package pokemon

import org.junit.Test
import org.junit.Assert

class UsarPiedraTest {
  @Test
  def `Usar Piedra Lunar en pokemon con condicionPiedra Lunar lo evoluciona`={
    val pokemon=Fixture.pokemonEvolucionPiedraLunar
    val pokemonEvolucionado=pokemon.realizar({_.usarPiedra(new Piedra(Lunar))}).get
    Assert.assertEquals(pokemon.especie.evolucion.get.especie, pokemonEvolucionado.especie)
  }
  
    @Test
  def `Usar Piedra con tipo en pokemon con condicion usar Piedra lo evoluciona`={
    val pokemon=Fixture.pokemonEvolucionPiedraTipoTierra
    val pokemonEvolucionado=pokemon.realizar({_.usarPiedra(new Piedra(Tierra))}).get
    Assert.assertEquals(pokemon.especie.evolucion.get.especie, pokemonEvolucionado.especie)
  }
    
        @Test
  def `Usar Piedra con tipo distinto al del pokemon y le gana, lo envenena`={
    val pokemon=Fixture.pokemonEvolucionPiedraTipoTierra
    val pokemonEvolucionado=pokemon.realizar({_.usarPiedra(new Piedra(Agua))}).get
    Assert.assertEquals(Envenenado, pokemonEvolucionado.estado)
  }
}