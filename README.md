# Requirements
・scala: 2.13.3

・sbt: 1.3.13

・aws-cdk: 1.57.0

# Deploy
1. Lambdaデプロイ先S3バケット作成
```
aws s3 mb s3://sam-sample --aws-profile=<AWSプロファイル>
aws s3 mb s3://sam-sample
```

```
npm run deploy
```

--------------------
#### デプロイしたLambdaの削除方法
・Webコンソールから該当のsamのCloudformationを削除する
or
・
```
aws cloudformation delete-stack --stack-name scala3-sample
```

--------------------
