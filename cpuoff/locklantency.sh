sudo perf record  -e syscalls:sys_enter_futex -e syscalls:sys_exit_futex -ga -- sleep 50
/opt/
