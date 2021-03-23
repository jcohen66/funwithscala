package com.example.patterns.builder

object Tv {
  sealed trait State
  sealed trait On extends State
  sealed trait Off extends State
}

case class Tv[S <: Tv.State]() {

  def turnOn(implicit ev: S =:= Tv.Off) = Tv[Tv.On]()
  def turnOff(implicit ev: S =:= Tv.On) = Tv[Tv.Off]()
}
