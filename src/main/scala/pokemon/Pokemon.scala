package pokemon

import scala.util.Try


case class Pokemon(experiencia:Int, genero:Genero,energiaOriginal:Int,energiaMaximaOriginal:Int,pesoOriginal:Int,
    fuerzaOriginal:Int,velocidadOriginal:Int,estado:Estado,especie:Especie,ataques:Map[Ataque,(Int,Int)]) {
  
  val fuerzaMaxima=100
  val velocidadMaxima=100
  val nivel:Int=especie.obtenerNivel(experiencia);
  val energiaMaxima=especie.aumentar(energiaMaximaOriginal,nivel);
  val energia:Int=minimo(energiaOriginal,energiaMaxima)
  val peso=especie.aumentar(pesoOriginal,nivel);
  val fuerza=minimo(especie.aumentar(fuerzaOriginal,nivel),fuerzaMaxima);
  val velocidad=minimo(especie.aumentar(velocidadOriginal,nivel),velocidadMaxima);
  if (nivel<1 || nivel >100) throw new NivelIncorrectoException
  if (peso<0 || peso>especie.pesoMaximo) throw new PesoInvalidoException
  if (fuerza<1) throw new FuerzaInvalidaException
  if (velocidad<1) throw new VelocidadInvalidaException
   
  
  def minimo(n1:Int,n2:Int):Int={
    if (n1<=n2) n1
    else n2
  }
  
  def subirExperiencia(exp:Int):Pokemon={
    var pokemonExperimentado = copy(experiencia=this.experiencia+exp)
    if (this.nivel<pokemonExperimentado.nivel){
      pokemonExperimentado=this.evolucionarSiCorrespondeNivel(pokemonExperimentado);
    }
    return pokemonExperimentado;
  }
  
  def evolucionarSiCorrespondeNivel(pokemon:Pokemon):Pokemon= {
    val especieNueva=especie.evolucionarSiCorrespondeNivel(pokemon)
  pokemon.copy(especie=especieNueva)
  }
  
  def usarAtaque(ataque:Ataque):Pokemon={
    val nuevosAtaques = this.ataques.map {case (a, (pa, pm)) => if (a == ataque) (a, (pa-1, pm)) else (a, (pa, pm))}
    ataque.aplicarEfecto(copy(ataques = nuevosAtaques))
    }
  
  def aprende(ataque:Ataque):Pokemon={
    copy(ataques=this.ataques.+((ataque,(ataque.maximoInicial,ataque.maximoInicial))))
  }
  
  
}

case class Poke(exp:Int){
  if(exp<0) throw new IllegalArgumentException
}