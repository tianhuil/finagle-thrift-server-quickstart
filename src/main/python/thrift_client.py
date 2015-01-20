from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from hello import Hello

try: 
  # Make socket
  transport = TSocket.TSocket('localhost', 8081)

  # Buffering is critical. Raw sockets are very slow
  transport = TTransport.TFramedTransport(transport)

  # Wrap in a protocol
  protocol = TBinaryProtocol.TBinaryProtocol(transport)

  # Create a client to use the protocol encoder
  client = Hello.Client(protocol)

  # Connect!
  transport.open()

  print 'hello: %s' % client.hi()
  transport.close()

except Thrift.TException, tx:
  print "%s" % (tx.message)
