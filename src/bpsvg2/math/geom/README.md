# `Differentiable`: Approximation using cubic Bézier curves

We wish to approximate a differentiable function $f: \mathbb{R} \to V$ on an interval $[a,b]$ with a cubic Bézier curve $P: [0, 1] \to V$ in the sense that, for $g(t) = f\big(a + (b-a)t\big)$, $P(t) \approx g(t)$.

Focusing on the end points, we wish to find parameters such that $P(0) = g(0)$, $P(1) = g(1)$, $P'(0) = g'(0)$, and $P'(1) = g'(1)$.

$P_0$ and $P_3$ are straightforward, with $P_0 = f(a)$ and $P_3 = f(b)$.

Using the fact that $P'(0) = 3(P_1 - P_0)$, we have that
$$P'(0) = 3(P_1 - P_0) = g'(0) = (b-a)f'(a)$$
$$P_1 = \frac{b - a}{3}f'(a) + P_0 =  \frac{b - a}{3}f'(a) + f(a)$$
Similarly,
$$P'(1) = 3(P_3 - P_2) = g'(1) = (b - a)f'(b)$$
$$P_2 = \frac{a - b}{3}f'(b) + P_3 = \frac{a - b}{3}f'(b) + f(b)$$

# `LSystem`: Self-similar images using `Use`

For each variable $X$, we assume there is a matching element `X0` in the document, likely in `defs`. When rendering, we maintain an orientation transform $A$ and a position transform $B$. When moving the position transform, we multiply on the left by the movement $M$ conjugated by the orientation transform, that is, $$B' = AMA^{-1}B$$

This motivates instead maintaining a partial position transform $P = A^{-1}B$. $B$ can easily be recovered by $B = AP$, while movements take the form $P' = MP$. Note that $P$ must also be updated when $A$ is updated, using $P' = A'^{-1}AP$.

Constants in L-systems then correspond to movements and changed to the orientation. Orientation changes are taken to be of the form $A' = MA$. Variables then correspond to sequence of elements and orientation and position transforms.