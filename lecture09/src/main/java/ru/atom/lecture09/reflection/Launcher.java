package ru.atom.lecture09.reflection;

/**
 * @author Alpi
 * @since 13.11.16
 */
public class Launcher {
    private static Service service;

    public static void main(String[] args)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        String serviceImplementationName = args[0];
        System.out.println("Service will be initialized with " + serviceImplementationName);
        Class<?> serviceImplClass = Class.forName(serviceImplementationName);
        service = (Service) serviceImplClass.newInstance();
        service.serve();
    }
}
