package org.discovery.dvms.entropy

/* ============================================================
 * Discovery Project - DVMS
 * http://beyondtheclouds.github.io/
 * ============================================================
 * Copyright 2013 Discovery Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============================================================ */

// TODO à fusionner avec dvms_scala/EntropyActor de SimgridInjector
import scheduling.dvms2.{SGNodeRef, SGActor}
import entropy.plan.choco.ChocoCustomRP
import entropy.plan.durationEvaluator.MockDurationEvaluator
import org.discovery.dvms.entropy.EntropyProtocol.MigrateVirtualMachine
import org.discovery.DiscoveryModel.model.ReconfigurationModel.{ReconfigurationlNoSolution, ReconfigurationResult}

class EntropyActor(applicationRef: SGNodeRef) extends AbstractEntropyActor(applicationRef) {

  val planner: ChocoCustomRP = new ChocoCustomRP(new MockDurationEvaluator(2, 5, 1, 1, 7, 14, 7, 2, 4));
  planner.setTimeLimit(3);

  //   def computeReconfigurationPlan(nodes: List[NodeRef]): Boolean = {

  def computeReconfigurationPlan(nodes: List[SGNodeRef]): ReconfigurationResult = {

//    val initialConfiguration: Configuration = new SimpleConfiguration();
//
//    // building the entropy configuration
//    var entropyResult: ReconfigurationResult = ReconfigurationlNoSolution()
//
//    try {
//
//      val physicalNodesWithVmsConsumption = Await.result(Future.sequence(nodes.map({
//        n =>
//          n.ref ? GetVmsWithConsumption()
//      })).mapTo[List[PhysicalNode]], 1 second)
//
//      physicalNodesWithVmsConsumption.foreach(physicalNodeWithVmsConsumption => {
//
//        val entropyNode = new SimpleNode(s"${physicalNodeWithVmsConsumption.ref.location.getId}",
//          physicalNodeWithVmsConsumption.specs.numberOfCPU,
//          physicalNodeWithVmsConsumption.specs.coreCapacity,
//          physicalNodeWithVmsConsumption.specs.ramCapacity);
//        initialConfiguration.addOnline(entropyNode);
//
//        physicalNodeWithVmsConsumption.machines.foreach(vm => {
//          val entropyVm = new SimpleVirtualMachine(vm.name,
//            vm.specs.numberOfCPU,
//            0,
//            vm.specs.ramCapacity,
//            vm.cpuConsumption.toInt,
//            vm.specs.ramCapacity);
//          initialConfiguration.setRunOn(entropyVm, entropyNode);
//        })
//      })
//
//      entropyResult = EntropyService.computeReconfigurationPlan(initialConfiguration, physicalNodesWithVmsConsumption)
//
//    } catch {
//      case e: Throwable =>
//        log.info("at least one virtual machines failed to answer in times")
//        entropyResult = ReconfigurationlNoSolution()
//    }
//    entropyResult
    println("""/!\ UNIMPLEMENTED /!\: EntropyActor.computeReconfigurationPlan()""");
    ReconfigurationlNoSolution()
  }


  override def receive(message: Object, sender: SGNodeRef, returnCanal: SGNodeRef) = message match {

    case MigrateVirtualMachine(vmName, destination) => {
      // Todo: reimplement this
      println("""/!\ UNIMPLEMENTED /!\: EntropyActor.receive: MigrateVirtualMachine(vmName, destination)""");

    }

    case msg => super.receive(msg, sender, returnCanal)
  }
}
