import java.util

case class Pokemon(experiencia:Int, genero:Genero,energia:Int,energiaMaximaOriginal:Int,pesoOriginal:Int,
    fuerzaOriginal:Int,velocidadOriginal:Int,estado:Estado,especie:Especie,ataques:util.List[Ataque]) {
  
  type Actividad=Any=>Try[Pokemon];
  
  def nivel:Int=especie.obtenerNivel(experiencia);
  def energiaMaxima=especie.aumentar(energiaMaximaOriginal,nivel);
  def peso=especie.aumentar(pesoOriginal,nivel);
  def fuerza=especie.aumentar(fuerzaOriginal,nivel);
  def velocidad=especie.aumentar(velocidadOriginal,nivel);
  
  
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
  
  
  val realizarUnAtaque:Actividad={
      case ataque:Ataque => ataques.filter(ataque == _).aplicarEfecto( this.subirExperiencia(ataque.tipo match{
        case Dragon => 80
        case this.especie.tipoPrincipal => 50
        case this.especie.tipoSecundario => this.genero match{
          case Macho => 20
          case Hembra => 40
        }
      })).getOrElse(new Exception);
  }

  val realizar(actividad:Actividad, elem:Object):Try[Pokemon]={
    this.estado match{
      case e:Dormido => copy(estado = e.dormir)
      case KO => new Exception()
      case _ => this.actividad(elem)
    }
  }

  val levanatarPesas(unosKilos:Int):Try[Pokemon]={
    this.estado match{
      case Paralizado => copy(estado = KO)
      case _ => if (unosKilos>10*tfuerza && this.especie.tipoPrincipal != Fantasma) {
        copy(estado = Paralizado)
      } else (this.especie.tipoPrincipal, this.especie.tipoSecundario) match{
        case (Fantasma, _) => new Exception()
        case (Pelea, _) => this.subirExperiencia(2*unosKilos)
        case (_, Some(Pelea)) => this.subirExperiencia(2*unosKilos)
        case _ => this.subirExperiencia(unosKilos)
      }
    }
  }
}