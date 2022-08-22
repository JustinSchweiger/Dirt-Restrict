package net.dirtcraft.plugins.dirtrestrict.utils.gradient;

@FunctionalInterface
public interface Interpolator {

	double[] interpolate(double from, double to, int max);
}