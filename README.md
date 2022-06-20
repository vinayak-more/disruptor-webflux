# disruptor-webflux
Using LMAX disruptor for processing request received from rest api. Disruptor is used to process the payload asychronously and flux is used to send response with Mono, making the Async backgroud workers responding Synchronous HTTP Request/Response. 
