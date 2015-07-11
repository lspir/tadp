package pokemon

import scala.util.Try
import scala.util.Failure
import scala.util.Failure
import scala.util.Success

case object Gimnasio {
  
   type Actividad=Pokemon=>Try[Pokemon];

   //Punto 3
   type Rutina = (String,List[Actividad])
   
   def ejecutarRutina(rutina:Rutina, pokemon:Pokemon):Try[Pokemon]=rutina match{
     case (_,actividades)=>actividades.foldLeft(Try(pokemon)){(tryPokemon, actividad) => tryPokemon.flatMap { poke => poke.realizar(actividad) }}
   }
   
   //Punto 4
   type Criterio=Pokemon=>Pokemon=>Boolean
   
   def mejorRutinaSegunCriterio(rutinas:List[Rutina], pokemon:Pokemon, criterio:Criterio):Option[String]={
     val rutinasValidas = rutinas.filter{rut=>ejecutarRutina(rut, pokemon).isSuccess }
     val posibleValor= rutinasValidas.sortWith{(left,right)=>criterio(ejecutarRutina(left, pokemon).get)(ejecutarRutina(right, pokemon).get)}.headOption
     posibleValor.map {_._1}
   }
   
        
}