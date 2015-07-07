package pokemon

import org.junit.Test
import org.junit.Assert

class AtaqueTest {
  @Test
  def `atacar con Ataque Mordida,al aprenderlo tiene max PA,luego al usarlo reduce en uno sus PA`={
   var pokemon=Fixture.pikachu
   pokemon=pokemon.aprende(Fixture.mordida)
   Assert.assertEquals(Fixture.mordida.maximoInicial, pokemon.ataques.get(Fixture.mordida).get._1)
   Assert.assertEquals(Fixture.mordida.maximoInicial, pokemon.ataques.get(Fixture.mordida).get._2)
   val pokemonPostAtaque=pokemon.usarAtaque(Fixture.mordida)
   Assert.assertEquals(Fixture.mordida.maximoInicial-1, pokemonPostAtaque.ataques.get(Fixture.mordida).get._1)
   Assert.assertEquals(Fixture.mordida.maximoInicial, pokemonPostAtaque.ataques.get(Fixture.mordida).get._2)
  }
  
  @Test 
  def `Al usar Reposar, aplica el efecto sobrre el pokemon incrementando la energia al maximo, pero durmiendolo`={
   var pokemon=Fixture.pikachu 
   pokemon=pokemon.aprende(Fixture.reposar)
   val pokemonPostReposar=pokemon.usarAtaque(Fixture.reposar)
   Assert.assertEquals(pokemon.energiaMaxima,pokemonPostReposar.energia)
   Assert.assertTrue(pokemonPostReposar.estado.isInstanceOf[Dormido])
  }
  
  @Test
  def `Realizar un Ataque, si el ataque es del tipo Dragon el pokemon gana 80P de experiencias`={
   val ataque=new Ataque(Dragon,30)
   val pokemonFuego=Fixture.pokemonFuego.aprende(ataque)
   val pokemonExperimentado=Gimnasio.realizar(Gimnasio.realizarUnAtaque(ataque), pokemonFuego)
   Assert.assertEquals(pokemonFuego.experiencia+80, pokemonExperimentado.get.experiencia)
   }
  @Test
  def `Realizar un Ataque, si es del Tipo Principal del Pokemon gana 50p de experiencia`={
    val ataque=Fixture.ataqueElectrico
    val pikachu=Fixture.pikachu.aprende(ataque)
    val pokemonExperimentado=Gimnasio.realizar(Gimnasio.realizarUnAtaque(ataque), pikachu)
    Assert.assertEquals(pikachu.experiencia+50, pokemonExperimentado.get.experiencia)
  }
  @Test
  def `Realizar un Ataque, si es del Tipo Secundario del Pokemon y es Macho gana 40p de experiencia`={
    val ataque=Fixture.ataqueVolador
    val zapdos=Fixture.zapdos.aprende(ataque)
    val pokemonExperimentado=Gimnasio.realizar(Gimnasio.realizarUnAtaque(ataque), zapdos)
    Assert.assertEquals(zapdos.experiencia+40, pokemonExperimentado.get.experiencia)
  }
}