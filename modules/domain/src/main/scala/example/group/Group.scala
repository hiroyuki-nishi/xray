package example.group

import domain.Value

case class GroupId(value: String) extends AnyVal with Value[String]
case class Group(id: GroupId)
