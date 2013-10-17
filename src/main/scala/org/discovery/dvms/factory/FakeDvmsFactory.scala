package org.discovery.dvms.factory

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

import org.discovery.dvms.monitor.{FakeMonitorActor, AbstractMonitorActor}
import org.discovery.dvms.entropy.{FakeEntropyActor, AbstractEntropyActor}
import org.discovery.AkkaArc.util.NodeRef
import org.discovery.dvms.dvms.DvmsActor
import org.discovery.dvms.log.LoggingActor


object FakeDvmsFactory extends DvmsAbstractFactory {

   def createMonitorActor(nodeRef: NodeRef): Option[AbstractMonitorActor] = {
      Some(new FakeMonitorActor(nodeRef))
   }

   def createDvmsActor(nodeRef: NodeRef): Option[DvmsActor] = {
      Some(new DvmsActor(nodeRef))
   }

   def createEntropyActor(nodeRef: NodeRef): Option[AbstractEntropyActor] = {
      Some(new FakeEntropyActor(nodeRef))
   }

   def createLoggingActor(nodeRef: NodeRef): Option[LoggingActor] = {
      Some(new LoggingActor(nodeRef.location))
   }
}
