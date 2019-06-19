package com.github.jswizzy

import helios.*
import helios.core.*
import arrow.core.Either
import arrow.core.getOrHandle


@json
data class Person(val name: String, val age: Int) {
    companion object
}

fun decode(jsonStr: String): String {
    val jsonFromString: Json =
        Json.parseFromString(jsonStr).getOrHandle {
            println("Failed creating the Json ${it.localizedMessage}, creating an empty one")
            JsString("")
        }

    val personOrError: Either<DecodingError, Person> = Person.decoder().decode(jsonFromString)

    return personOrError.fold({
        "Something went wrong during decoding: $it"
    }, {
        "Successfully decode the json: $it"
    })
}

fun encode(person: Person): String {
    val jsonFromPerson: Json = with(Person.encoder()) {
        person.encode()
    }

    return jsonFromPerson.toJsonString()
}

fun main() {
    val jsonStr =
        """{
     "name": "Simon",
     "age": 30
   }"""

    println(decode(jsonStr))

    val person = Person("Raul", 34)

    println(encode(person))
}