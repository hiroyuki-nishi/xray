package example.group

import example.ApplicationError
import example.ApplicationErrorConverter._

trait GroupUsecase {
  protected val groupRepository: GroupRepository

  def run(): Either[ApplicationError, Group] =
    groupRepository.findBy(Group(GroupId("TEST"))).toApplicationError("error")
}
