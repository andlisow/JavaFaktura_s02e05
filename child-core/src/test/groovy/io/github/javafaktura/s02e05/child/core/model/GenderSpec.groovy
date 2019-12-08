package io.github.javafaktura.s02e05.child.core.model


import spock.lang.Specification
import spock.lang.Unroll

class GenderSpec extends Specification {

    @Unroll
    def "For given name = #name should detect right gender = #expectedGender"() {
        expect:
        expectedGender == Gender.fromName(name)
        where:
        name            || expectedGender
        "JANUSZ"        || Gender.MALE
        "ANNA"          || Gender.FEMALE
        "JAVAFAKTURA"   || Gender.FEMALE
    }
}
