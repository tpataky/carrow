import cats.kernel.Semigroup

object Main {

  trait CArrow[K[_], F[_, _, _]] {
    def lift[A: K, B](f: A => B): F[A, B, K]

    def id[A]: F[A, A, K]

    def compose[A, B, C](f: F[B, C, K], g: F[A, B, K]): F[A, C, K]

    def andThen[A, B, C](f: F[A, B, K], g: F[B, C, K]): F[A, C, K] =
      compose(g, f)

    def first[A, B, C](fa: F[A, B, K]): F[(A, C), (B, C), K]

    def second[A, B, C](fa: F[A, B, K]): F[(C, A), (C, B), K]

    def split[A, B, C, D](f: F[A, B, K], g: F[C, D, K]): F[(A, C), (B, D), K]

    def dimap[A, B, C: K, D](fab: F[A, B, K])(f: C => A)(g: B => D): F[C, D, K]

    def lmap[A, B, C: K](fab: F[A, B, K])(f: C => A): F[C, B, K] =
      dimap(fab)(f)(identity)

    def rmap[A, B, C](fab: F[A, B, K])(f: B => C): F[A, C, K] =
      dimap[A, B, A, C](fab)(identity)(f)
  }


  trait Calc[A, B]{}

  implicit def calcCArrow[A, B]: CArrow[Semigroup]
}