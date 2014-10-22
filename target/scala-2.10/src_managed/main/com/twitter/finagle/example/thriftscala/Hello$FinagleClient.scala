/**
 * Generated by Scrooge
 *   version: 3.14.1
 *   rev: a996c1128a032845c508102d62e65fc0aa7a5f41
 *   built at: 20140501-114733
 */
package com.twitter.finagle.example.thriftscala

import com.twitter.finagle.{SourcedException, Service => FinagleService}
import com.twitter.finagle.stats.{NullStatsReceiver, StatsReceiver}
import com.twitter.finagle.thrift.ThriftClientRequest
import com.twitter.scrooge.{ThriftStruct, ThriftStructCodec}
import com.twitter.util.{Future, Return, Throw}
import java.nio.ByteBuffer
import java.util.Arrays
import org.apache.thrift.protocol._
import org.apache.thrift.TApplicationException
import org.apache.thrift.transport.{TMemoryBuffer, TMemoryInputTransport}
import scala.collection.{Map, Set}


@javax.annotation.Generated(value = Array("com.twitter.scrooge.Compiler"))
class Hello$FinagleClient(
  val service: FinagleService[ThriftClientRequest, Array[Byte]],
  val protocolFactory: TProtocolFactory = new TBinaryProtocol.Factory,
  val serviceName: String = "",
  stats: StatsReceiver = NullStatsReceiver
) extends Hello[Future] {
  import Hello._

  protected def encodeRequest(name: String, args: ThriftStruct) = {
    val buf = new TMemoryBuffer(512)
    val oprot = protocolFactory.getProtocol(buf)

    oprot.writeMessageBegin(new TMessage(name, TMessageType.CALL, 0))
    args.write(oprot)
    oprot.writeMessageEnd()

    val bytes = Arrays.copyOfRange(buf.getArray, 0, buf.length)
    new ThriftClientRequest(bytes, false)
  }

  protected def decodeResponse[T <: ThriftStruct](resBytes: Array[Byte], codec: ThriftStructCodec[T]) = {
    val iprot = protocolFactory.getProtocol(new TMemoryInputTransport(resBytes))
    val msg = iprot.readMessageBegin()
    try {
      if (msg.`type` == TMessageType.EXCEPTION) {
        val exception = TApplicationException.read(iprot) match {
          case sourced: SourcedException =>
            if (serviceName != "") sourced.serviceName = serviceName
            sourced
          case e => e
        }
        throw exception
      } else {
        codec.decode(iprot)
      }
    } finally {
      iprot.readMessageEnd()
    }
  }

  protected def missingResult(name: String) = {
    new TApplicationException(
      TApplicationException.MISSING_RESULT,
      name + " failed: unknown result"
    )
  }

  protected def setServiceName(ex: Exception): Exception =
    if (this.serviceName == "") ex
    else {
      ex match {
        case se: SourcedException =>
          se.serviceName = this.serviceName
          se
        case _ => ex
      }
    }

  // ----- end boilerplate.

  private[this] val scopedStats = if (serviceName != "") stats.scope(serviceName) else stats
  private[this] object __stats_hi {
    val RequestsCounter = scopedStats.scope("hi").counter("requests")
    val SuccessCounter = scopedStats.scope("hi").counter("success")
    val FailuresCounter = scopedStats.scope("hi").counter("failures")
    val FailuresScope = scopedStats.scope("hi").scope("failures")
  }
  
  def hi(): Future[String] = {
    __stats_hi.RequestsCounter.incr()
    this.service(encodeRequest("hi", hi$args())) flatMap { response =>
      val result = decodeResponse(response, hi$result)
      val exception: Future[Nothing] =
        null
  
      if (result.success.isDefined)
        Future.value(result.success.get)
      else if (exception != null)
        exception
      else
        Future.exception(missingResult("hi"))
    } respond {
      case Return(_) =>
        __stats_hi.SuccessCounter.incr()
      case Throw(ex) =>
        __stats_hi.FailuresCounter.incr()
        __stats_hi.FailuresScope.counter(ex.getClass.getName).incr()
    }
  }
}