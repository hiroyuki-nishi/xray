# Requirements
・scala: 2.13.3

・sbt: 1.3.13

・aws-cdk: 1.57.0

1. Lambdaデプロイ先S3バケット作成
```
aws s3 mb s3://sam-sample --aws-profile=<AWSプロファイル>
aws s3 mb s3://sam-sample
```

2. sam package で、上で作成したS3バケットへ実行ファイルをアップロード & デプロイ用テンプレートファイルを生成します
```
sam package --s3-bucket sam-sample --output-template-file output.yml
```

3.sam deploy で、Lambdaをデプロイします。
```
sam deploy --template-file output.yml --stack-name scala3-sample --capabilities CAPABILITY_IAM
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
