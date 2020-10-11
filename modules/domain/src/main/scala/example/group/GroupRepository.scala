package example.group

import example.RepositoryError

trait GroupRepository {
  def findBy(group: Group): Either[RepositoryError, Group]
}
