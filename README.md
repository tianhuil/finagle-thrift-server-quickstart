finagle-thrift-server-quickstart
================================

This project illustrates how to start working on a [Finagle](https://twitter.github.io/finagle/) [Thrift](https://thrift.apache.org/) Server.

It also includes Scala code generation via [Scrooge](http://twitter.github.io/scrooge/).

# Run the example
-----------------

1. Compile thrift for python into `gen-py/*`

	```thrift --gen py src/main/thrift/hello.thrift```

2. Compile and start the scala server:

	```sbt run```

3. In a new window, run the python client

	```PYTHONPATH=$PYTHONPATH:./gen-py python src/main/python/thrift_client.py```
