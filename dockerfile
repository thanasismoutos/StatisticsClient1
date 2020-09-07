

# Shared Volume
 FROM busybox:buildroot-2014.02
 MAINTAINER first last, first.last@yourdomain.com
 VOLUME /data
 CMD ["/bin/sh"]
f = BytesIO(dockerfile.encode('utf-8'))
cli=Client(base_url='tcp://127.0.0.1:2375')
response= [line for line in cli.build(
	fileobj=f, rm=True, tag='statclient'
)]
response
