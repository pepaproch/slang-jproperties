var NOT_LOCAL;

fun localVariables() {
  var localVar;
  var INVALID_LOCAL; // Noncompliant {{Rename this local variable to match the regular expression "^[_a-z][a-zA-Z0-9]*$".}}
//    ^^^^^^^^^^^^^
  var invalid_local; // Noncompliant
}

fun parameters(param1, PARAM2, _) { // Noncompliant {{Rename this parameter to match the regular expression "^[_a-z][a-zA-Z0-9]*$".}}
//                     ^^^^^^
}

native [] {
  [
    var POSSIBLY_NOT_LOCAL;
  ]
};
