import boto3, json, os, argparse, zipfile, glob, botocore

def update_lambda(client, function_name, zip_path, profile_name, region):
	"""
	更新lambda函数代码
	:param client: boto3连接
	:param function_name:lambda函数名称
	:param zip_path:本机源代码存储路径
	:param profile_name:使用aws-cli配置文件名称
	:param region:aws region
	:return:
	"""
	with open(zip_path, 'rb') as zip_blob:
		response = client.update_function_code(
			FunctionName=function_name,
			ZipFile=zip_blob.read()
		)
		print(response)


def main():
	# 更新lambda函数代码
	parser = argparse.ArgumentParser(description='Execute input file. Supports only python or sh file.')
	#lambda函数名称
	parser.add_argument('-lambda_function', '--function_name', required=True, help='Lambda function name')
	#本机源代码存储路径
	parser.add_argument('-file_path', '--zip_path', required=True, help='the path of zip file')
	#使用aws-cli配置文件名称
	parser.add_argument('-profile', '--profile_name', required=True, help='profile name for aws key')
	#aws region
	parser.add_argument('-region', '--region', required=False, help='region name')
	args = parser.parse_args()
	# 获取入参
	function_name = args.function_name
	zip_path = args.zip_path
	profile_name = args.profile_name
	region = args.region

	client = boto3.session.Session(profile_name=profile_name, region_name=region).client('lambda')
	update_lambda(client, function_name, zip_path, profile_name, region)


if __name__ == '__main__':
	main()
