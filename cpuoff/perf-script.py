# perf script event handlers, generated by perf script -g python
# Licensed under the terms of the GNU GPL License version 2

# The common_* event handler fields are the most useful fields common to
# all events.  They don't necessarily correspond to the 'common_*' fields
# in the format files.  Those fields not available as handler params can
# be retrieved using Python functions of the form common_*(context).
# See the perf-trace-python Documentation for the list of available functions.

import os
import sys

sys.path.append(os.environ['PERF_EXEC_PATH'] + \
	'/scripts/python/Perf-Trace-Util/lib/Perf/Trace')

from perf_trace_context import *
from Core import *


def trace_begin():
	print "in trace_begin"

def trace_end():
	print "in trace_end"

def syscalls__sys_exit_futex(event_name, context, common_cpu,
	common_secs, common_nsecs, common_pid, common_comm,
	common_callchain, nr, ret):
		print_header(event_name, common_cpu, common_secs, common_nsecs,
			common_pid, common_comm)

		print "nr=%d, ret=%d" % \
		(nr, ret)

		for node in common_callchain:
			if 'sym' in node:
				print "\t[%x] %s" % (node['ip'], node['sym']['name'])
			else:
				print "	[%x]" % (node['ip'])

		print "\n"

def syscalls__sys_enter_futex(event_name, context, common_cpu,
	common_secs, common_nsecs, common_pid, common_comm,
	common_callchain, nr, uaddr, op, val, 
	utime, uaddr2, val3):
		print_header(event_name, common_cpu, common_secs, common_nsecs,
			common_pid, common_comm)

		print "nr=%d, uaddr=%u, op=%u, " \
		"val=%u, utime=%u, uaddr2=%u, " \
		"val3=%u" % \
		(nr, uaddr, op, val, 
		utime, uaddr2, val3)

		for node in common_callchain:
			if 'sym' in node:
				print "\t[%x] %s" % (node['ip'], node['sym']['name'])
			else:
				print "	[%x]" % (node['ip'])

		print "\n"

def trace_unhandled(event_name, context, event_fields_dict):
		print ' '.join(['%s=%s'%(k,str(v))for k,v in sorted(event_fields_dict.items())])

def print_header(event_name, cpu, secs, nsecs, pid, comm):
	print "%-20s %5u %05u.%09u %8u %-20s " % \
	(event_name, cpu, secs, nsecs, pid, comm),
