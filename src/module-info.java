module app {
	requires undertow.core;
	requires gson;
	requires xnio.api;
	requires xnio.nio;
	requires jboss.logging;

	requires java.sql;

	exports app.domain;
}