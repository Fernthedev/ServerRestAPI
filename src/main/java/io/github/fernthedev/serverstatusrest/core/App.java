package io.github.fernthedev.serverstatusrest.core;

import io.github.fernthedev.serverstatusrest.Core;

import javax.inject.Singleton;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Feature;
import java.util.HashSet;
import java.util.Set;

//@ApplicationPath(value = Constants.MAIN_PATH)
@Singleton
public class App extends Application {

    /**
     * Get a set of root resource, provider and {@link Feature feature} classes.
     * <p>
     * The default life-cycle for resource class instances is per-request. The default
     * life-cycle for providers (registered directly or via a feature) is singleton.
     * <p>
     * Implementations should warn about and ignore classes that do not
     * conform to the requirements of root resource or provider/feature classes.
     * Implementations should warn about and ignore classes for which
     * {@link #getSingletons()} returns an instance. Implementations MUST
     * NOT modify the returned set.
     * </p>
     * <p>
     * The default implementation returns an empty set.
     * </p>
     *
     * @return a set of root resource and provider classes. Returning {@code null}
     * is equivalent to returning an empty set.
     */
    @Override
    public Set<Class<?>> getClasses() {
//        return super.getClasses();
        return new HashSet<>(Core.getRestHandlers());
    }
}
