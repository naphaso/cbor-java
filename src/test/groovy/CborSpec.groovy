import com.naphaso.cbor.Cbor
import spock.lang.Specification

/**
 * Created by wolong on 04/02/14.
 */
class CborSpec extends Specification {

    def "int"() {
        when:
        int a = 123;
        byte[] data = Cbor.serialize(a);
        int b = Cbor.deserialize(data);

        then:
        a == b
    }

    def "string"() {
        when:
        String a = "hello world";
        byte[] data = Cbor.serialize(a);
        String b = Cbor.deserialize(data);

        then:
        a == b
    }

    def "map"() {
        when:
        Map a = new HashMap()
        a.put("hello", "world")
        a.put("one", "two")
        a.put("some", "value")
        byte[] data = Cbor.serialize(a);
        Map b = Cbor.deserialize(data);

        then:
        a == b
    }

}
