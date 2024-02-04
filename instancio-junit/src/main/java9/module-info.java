module org.instancio.junit {
  requires transitive org.instancio.core;

  requires org.slf4j;
  requires org.junit.jupiter.api;
  requires org.junit.jupiter.params;

  exports org.instancio.junit;
}