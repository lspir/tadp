package pokemon

import scala.util.Try
import scala.util.Failure

case object Gimnasio {
  
  type Actividad[T]=T=>Pokemon=>Try[Pokemon];
  
    val realizarUnAtaque:Actividad[Ataque]={
      case ataque:Ataque => {case pokemon:Pokemon => Try(pokemon.ataques.filter(ataque == _).head.aplicarEfecto((pokemon).subirExperiencia(ataque.tipo match{
        case Dragon => 80
        case pokemon.especie.tipoPrincipal => 50
        case pokemon.especie.tipoSecundario => pokemon.genero match{
          case Macho => 20
          case Hembra => 40
        } //FIXME VER PUNTOS DE ATAQUE
      })))}
   }

 
  def realizar[T](actividad:(Pokemon=>Try[Pokemon]),pokemon:Pokemon):Try[Pokemon]={
    pokemon.estado match {
      case e:Dormido => Try(pokemon.copy(estado = e.dormir))
      case KO => Failure(new Exception)
      case _ => actividad(pokemon)
    }
  }  
  
  
  val levantarPesas:Actividad[Int]={
    case kilos:Int => {
      case pokemon:Pokemon=>pokemon.estado match{
        case _ if pokemon.especie.sosDeTipo(Fantasma) => Failure(new Exception)
        case Paralizado => Try(pokemon.copy(estado=KO))
        case _ if kilos>pokemon.fuerza*10 => Try(pokemon.copy(estado=Paralizado))
        case _ if pokemon.especie.sosDeTipo(Pelea)=>Try(pokemon.subirExperiencia(2*kilos))
        case _ => Try(pokemon.subirExperiencia(kilos))
      }
    }
  }
  val nadar:Actividad[Int]={
    case minutos:Int => {
      case pokemon:Pokemon if pokemon.especie.perdesContra(Agua) => Try(pokemon.copy(estado = KO))
      case pokemon:Pokemon if pokemon.especie.sosDeTipo(Agua) => Try(pokemon.copy(velocidadOriginal = pokemon.velocidad + minutos/60, energia = pokemon.energia + minutos).subirExperiencia(200*minutos))
      case pokemon:Pokemon => Try(pokemon.copy(energia = pokemon.energia - minutos).subirExperiencia(200*minutos))
    }
  }
  
  
  
  
  val prueba:Int=>String=>Try[Int]={
    case i1:Int=>{case s:String=>Try(i1)}
  }
}
  



//Gimnasio.realizar(actividad(new Ataque)(pokemon))