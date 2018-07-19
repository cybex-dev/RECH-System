package helpers;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FileScanner {

    public List<File> getResourceFiles(String path) throws IOException {
        List<String> filenames = new ArrayList<>();

        try (
                InputStream in = getResourceAsStream(path);
                BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String resource;
            while ((resource = br.readLine()) != null) {
                filenames.add(resource);
            }
        }

        List<File> files = new ArrayList<>();
        filenames.forEach(s -> {
            try {
                URI fileFromResource = getFileFromResource(path + s);
                File f = new File(fileFromResource);
                if (f.exists()) {
                    files.add(f);
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        });

        return files;
    }

    public InputStream getResourceAsStream(String resource) {
        final InputStream in
                = getContextClassLoader().getResourceAsStream(resource);

        return in == null ? getClass().getResourceAsStream(resource) : in;
    }

    public URI getFileFromResource(String path) throws URISyntaxException {
        final URL url = getClass().getResource(path);
        return url == null ? null : url.toURI();
    }

    private ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

}
