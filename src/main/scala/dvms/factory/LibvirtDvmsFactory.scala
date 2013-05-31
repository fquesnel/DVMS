package dvms.factory

import dvms.monitor.{LibvirtMonitorActor, AbstractMonitorActor}
import dvms.entropy.{EntropyActor, AbstractEntropyActor}
import org.bbk.AkkaArc.util.NodeRef
import dvms.dvms.DvmsActor


/**
 * Created with IntelliJ IDEA.
 * User: jonathan
 * Date: 3/25/13
 * Time: 3:53 PM
 * To change this template use File | Settings | File Templates.
 */

object LibvirtDvmsFactory extends DvmsAbstractFactory {

   def createMonitorActor(nodeRef: NodeRef): Option[AbstractMonitorActor] = {
      Some(new LibvirtMonitorActor(nodeRef))
   }

   def createDvmsActor(nodeRef: NodeRef): Option[DvmsActor] = {
      Some(new DvmsActor(nodeRef))
   }

   def createEntropyActor(nodeRef: NodeRef): Option[AbstractEntropyActor] = {
      Some(new EntropyActor(nodeRef))
   }
}