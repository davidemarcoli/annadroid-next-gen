/*******************************************************************/
/* Example for the MCR */
/****************===================================****************/
/* Use of the following functions: */
/* KRN_attach_device, KRN_detach_device, KRN_get_next_event */
/* MCR_enable_ISOtrack */
/*******************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <toolbox.h>

int main(int argc, char *argv[])
{
    int res,i;
    int origine_q;
    KRN_Event event;
    int origine_id;

    /* Attach the Magnetic Card Reader device with infinite timeout */
    res = KRN_attach_device(DEV_MCR, TIM_INF);
    cprintf("res from KRN attach device = %d\n", res);

    /* Enable the MCR , timeout = 20 sec*/
    res = MCR_enable_ISOtrack (20*1000);

    cprintf("res from MCR_enable_ISOtrack = %d\n", res);

    /* Wait the next event from MCR device , with a timeout = 30 sec */
    res = KRN_get_next_event(Q_MCR,&origine_q,&event,30000,&origine_id);
    cprintf("res from KRN get next event = %d\n", res);

    /* If the event is different than a MVR_evt_data, the program stop */
    if (event.event_id != MCR_EVT_DATA)
    return(-1);

    /* Print the result from MCR */
    cprintf("data = %d\n",event.data);

    for(i=0; i<event.len; i++)
    cprintf("buffer[%d] = %d\n",i,event.buffer[i]);

    /* Detach the MCR device */
    res = KRN_detach_device(DEV_MCR);
    cprintf("res from KRN detach device = %d\n", res);

    return(0);
}
