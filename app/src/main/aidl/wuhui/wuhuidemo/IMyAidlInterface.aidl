// IMyAidlInterface.aidl
package wuhui.wuhuidemo;

// Declare any non-default types here with import statements

interface IMyAidlInterface {
  void hello(String msg);
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
                                                        double aDouble, String aString);

}
