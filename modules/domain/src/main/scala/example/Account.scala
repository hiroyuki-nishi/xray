package example

import domain.Value

case class AccountId(value: String) extends AnyVal with Value[String]
