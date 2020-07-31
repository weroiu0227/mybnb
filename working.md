# Kubectl install

# AWS-Cli v2 install

# AWS Configure
- Access Key ID : 
- Secret Access Key : 
- Region : ap-northeast-2
- Default output format : json
 
# EKS Cluster create
<pre>
eksctl create cluster --name jihwancha-cluster --version 1.15 --nodegroup-name standard-workers --node-type t3.medium --nodes 2 --nodes-min 1 --nodes-max 3
</pre>

# EKS Cluster settings
<pre>
aws eks --region ap-northeast-2 update-kubeconfig --name jihwancha-cluster
kubectl config current-context
kubectl get all
</pre>

# ECR 인증
- Dockerhub 사용으로 진행할 필요 없음


# EKS Cluster delete
<pre>
eksctl delete cluster --name jihwancha-cluster
</pre>

# Metric Server 설치
<pre>
kubectl apply -f https://github.com/kubernetes-sigs/metrics-server/releases/download/v0.3.6/components.yaml
kubectl get deployment metrics-server -n kube-system
</pre>

# Kafka install (kubernetes/helm)
참고 - (https://workflowy.com/s/msa/27a0ioMCzlpV04Ib#/a7018fb8c62)

<pre>
curl https://raw.githubusercontent.com/kubernetes/helm/master/scripts/get | bash
kubectl --namespace kube-system create sa tiller      
kubectl create clusterrolebinding tiller --clusterrole cluster-admin --serviceaccount=kube-system:tiller
helm init --service-account tiller
kubectl patch deploy --namespace kube-system tiller-deploy -p '{"spec":{"template":{"spec":{"serviceAccount":"tiller"}}}}'

helm repo add incubator http://storage.googleapis.com/kubernetes-charts-incubator
helm repo update

helm install --name my-kafka --namespace kafka incubator/kafka
</pre>

# Kafka delete
<pre>
helm del my-kafka  --purge
</pre>

# Lab. Istio 설치
<pre>
curl -L https://git.io/getLatestIstio | ISTIO_VERSION=1.4.5 sh -
cd istio-1.4.5
export PATH=$PWD/bin:$PATH
for i in install/kubernetes/helm/istio-init/files/crd*yaml; do kubectl apply -f $i; done
kubectl apply -f install/kubernetes/istio-demo.yaml

kubectl get pod -n istio-system
</pre>

# kiali 설치 

* 에러발생, 위의 istio 설치에서 완료됨
<pre>
vi kiali.yaml    

apiVersion: v1
kind: Secret
metadata:
  name: kiali
  namespace: istio-system
  labels:
    app: kiali
type: Opaque
data:
  username: YWRtaW4=
  passphrase: YWRtaW4=

----- save (:wq)

kubectl apply -f kiali.yaml
helm template --set kiali.enabled=true install/kubernetes/helm/istio --name istio --namespace istio-system > kiali_istio.yaml    
kubectl apply -f kiali_istio.yaml
</pre>

* load balancer로 변경
<pre>
kubectl edit service/kiali -n istio-system
(ClusterIP -> LoadBalancer)
</pre>

# namespace create
<pre>
kubectl create namespace mybnb
</pre>
* istio enabled
<pre>
kubectl label namespace mybnb istio-injection=enabled
</pre>

# siege deploy
<pre>
cd mybnb/yaml
kubectl apply -f siege.yaml 
kubectl exec -it siege -n mybnb -- /bin/bash
apt-get update
apt-get install httpie
</pre>

# image build & push
<pre>
cd mybnb/html
docker build -t jihwancha/mybnb-html:v1 .
docker push jihwancha/mybnb-html:v1

...
</pre>

# application deploy
<pre>
cd mybnb/yaml

kubectl apply -f gateway.yaml
kubectl apply -f html.yaml
kubectl apply -f room.yaml
kubectl apply -f booking.yaml
kubectl apply -f pay.yaml
kubectl apply -f mypage.yaml

kubectl apply -f alarm.yaml
kubectl apply -f review.yaml
</pre>

# 숙소 등록 (siege 에서)
<pre>
http POST http://room:8080/rooms name=“호텔” price=1000 address=“서울” host=“Superman”
http POST http://room:8080/rooms name=“펜션” price=1000 address=“양평” host=“Superman”
http POST http://room:8080/rooms name=“민박” price=1000 address=“강릉” host=“Superman”
</pre>

# 예약 (siege 에서)
<pre>
http POST http://booking:8080/bookings roomId=1 name=“호텔” price=1000 address=“서울” host=“Superman” guest=“배트맨” usedate=“20201010”
</pre>

# 예약 부하 발생 (siege 에서)
<pre>
siege -v -c100 -t60S -r10 --content-type "application/json" 'http://booking:8080/bookings POST {"roomId":1, "name":"호텔", "price":1000, "address":"서울", "host":"Superman", "guest":"배트맨”, “usedate”:”20201230”}'
</pre>

# kiali 확인
* 접속정보 : ip
<pre>
http://external-ip:20001 
(admin/admin)
</pre>

# auto scale out settings
<pre>
kubectl autoscale deploy booking -n mybnb --min=1 --max=10 --cpu-percent=15
kubectl get deploy booking -n mybnb -w
</pre>

# auto scale out delete
<pre>
kubectl delete hpa booking -n mybnb
</pre>

#  부하 발생 (siege 에서)
<pre>
siege -v -c100 -t60S -r10 --content-type "application/json" 'http://booking:8080/bookings'
</pre>







