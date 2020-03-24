package net.qqxh.common.ossclient;

public class MinioTemplate  {
/*    private final String endpoint;
    private final String accessKey;
    private final String secretKey;
    private MinioClient client;

    *//**
     * 创建bucket
     *
     * @param bucketName bucket名称
     *//*
    public void createBucket(String bucketName) {
        if (!client.bucketExists(bucketName)) {
            client.makeBucket(bucketName);
        }
    }

    *//**
     * 获取全部bucket
     * <p>
     * https://docs.minio.io/cn/java-client-api-reference.html#listBuckets
     *//*
    public List<CouchbaseProperties.Bucket> getAllBuckets() {
        return client.listBuckets();
    }

    *//**
     * 根据bucketName获取信息
     * @param bucketName bucket名称
     *//*
    public Optional<Bucket> getBucket(String bucketName) {
        return client.listBuckets().stream().filter(b -> b.name().equals(bucketName)).findFirst();
    }

    *//**
     * 根据bucketName删除信息
     * @param bucketName bucket名称
     *//*
    @SneakyThrows
    public void removeBucket(String bucketName) {
        client.removeBucket(bucketName);
    }

    *//**
     * 根据文件前置查询文件
     *
     * @param bucketName bucket名称
     * @param prefix     前缀
     * @param recursive  是否递归查询
     * @return MinioItem 列表
     *//*
    @SneakyThrows
    public List<MinioItem> getAllObjectsByPrefix(String bucketName, String prefix, boolean recursive) {
        List<MinioItem> objectList = new ArrayList<>();
        Iterable<Result<Item>> objectsIterator = client
                .listObjects(bucketName, prefix, recursive);

        while (objectsIterator.iterator().hasNext()) {
            objectList.add(new MinioItem(objectsIterator.iterator().next().get()));
        }
        return objectList;
    }

    *//**
     * 获取文件外链
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param expires    过期时间 <=7
     * @return url
     *//*
    @SneakyThrows
    public String getObjectURL(String bucketName, String objectName, Integer expires) {
        return client.presignedGetObject(bucketName, objectName, expires);
    }

    *//**
     * 获取文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @return 二进制流
     *//*
    @SneakyThrows
    public InputStream getObject(String bucketName, String objectName) {
        return client.getObject(bucketName, objectName);
    }

    *//**
     * 上传文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param stream     文件流
     * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#putObject
     *//*
    public void putObject(String bucketName, String objectName, InputStream stream) throws Exception {
        client.putObject(bucketName, objectName, stream, stream.available(), "application/octet-stream");
    }

    *//**
     * 上传文件
     *
     * @param bucketName  bucket名称
     * @param objectName  文件名称
     * @param stream      文件流
     * @param size        大小
     * @param contextType 类型
     * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#putObject
     *//*
    public void putObject(String bucketName, String objectName, InputStream stream, long size, String contextType) throws Exception {
        client.putObject(bucketName, objectName, stream, size, contextType);
    }

    *//**
     * 获取文件信息
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#statObject
     *//*
    public ObjectStat getObjectInfo(String bucketName, String objectName) throws Exception {
        return client.statObject(bucketName, objectName);
    }

    *//**
     * 删除文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#removeObject
     *//*
    public void removeObject(String bucketName, String objectName) throws Exception {
        client.removeObject(bucketName, objectName);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.hasText(endpoint, "Minio url 为空");
        Assert.hasText(accessKey, "Minio accessKey为空");
        Assert.hasText(secretKey, "Minio secretKey为空");
        this.client = new MinioClient(endpoint, accessKey, secretKey);
    }


    文件上传方法:
    public String upload(@RequestParam("file") MultipartFile file) {
        String fileName  = file.getOriginalFilename();
        Map<String, String> resultMap = new HashMap<>(4);
        resultMap.put("bucketName", "bucketName");
        resultMap.put("fileName", fileName);
        try {
            minioTemplate.putObject("bucketName", fileName, file.getInputStream());

　　} catch (Exception e) {
            return "上传失败";
        }
        return "上传成功";
    }


    文件下载方法:
    public void download(String fileName, HttpServletResponse response, HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        String[] nameArray = StrUtil.split(fileName, "-");
        try (InputStream inputStream = minioTemplate.getObject(nameArray[0], nameArray[1])) {
            //解决乱码
            if ( //IE 8 至 IE 10
                    userAgent.toUpperCase().contains("MSIE") ||
                            //IE 11
                            userAgent.contains("Trident/7.0")) {
                fileName = java.net.URLEncoder.encode(nameArray[1], "UTF-8");
            } else{
                fileName = new String(nameArray[1].getBytes("UTF-8"),"iso-8859-1");
            }
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setContentType("application/force-download");
            response.setCharacterEncoding("UTF-8");
            IoUtil.copy(inputStream, response.getOutputStream());
        } catch (Exception e) {
            log.error("文件读取异常", e);
        }*/
    }
