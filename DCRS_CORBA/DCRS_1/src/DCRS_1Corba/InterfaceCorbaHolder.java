package DCRS_1Corba;

/**
* DCRS_1Corba/InterfaceCorbaHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from C:/Users/iknoor/eclipse-workspace2018/DCRS_1/bin/InterfaceCorba.idl
* Sunday, November 4, 2018 4:05:19 PM EST
*/

public final class InterfaceCorbaHolder implements org.omg.CORBA.portable.Streamable
{
  public DCRS_1Corba.InterfaceCorba value = null;

  public InterfaceCorbaHolder ()
  {
  }

  public InterfaceCorbaHolder (DCRS_1Corba.InterfaceCorba initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = DCRS_1Corba.InterfaceCorbaHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    DCRS_1Corba.InterfaceCorbaHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return DCRS_1Corba.InterfaceCorbaHelper.type ();
  }

}
