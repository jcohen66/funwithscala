package com.example.futures

import java.math.BigInteger
import java.net.URL
import java.security.MessageDigest
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.util.control.NonFatal
import scala.util.{Failure, Success}

object Main extends App {
  type Name = String
  type Email = String
  type Password = String
  type Avatar = URL

  case class User(name: Name, email: Email, password: Password, avatar: Avatar)

  def notExist(email: Email): Future[Boolean] = Future {
    Thread.sleep(100)
    true
  }

  def md5hash(str: String): String =
    new BigInteger(1,
      MessageDigest
        .getInstance("MD5")
        .digest(str.getBytes())
    ).toString(16)

  def avatar(email: Email): Future[Avatar] = Future {
    Thread.sleep(200)
    new Avatar("http://avaatar.example.com/user/23K520f23f4.png")
  }

  def createUser(name: Name, email: Email, password: Password): Future[User] =
    for {
      _ <- notExist(email)
      avatar <- avatar(email)
      hashedPassword = md5hash(password)
    } yield User(name, email, hashedPassword, avatar)

  def runByPromise[T](block: => T)(implicit ec: ExecutionContext): Future[T] = {
    val p = Promise[T]
    ec.execute { () =>
      try {
        p.success(block)
      } catch {
        case NonFatal(e) => p.failure(e)
      }
    }
    p.future
  }

  val userFuture: Future[User] = createUser("John", "John@emaple.com", "secret")

  userFuture.onComplete {
    case Success(user) => println(s"User created: $user")
    case Failure(exception: Exception) =>
      println(s"Creating user failed due to the exception: $exception")
  }

  while(true) {}
}

